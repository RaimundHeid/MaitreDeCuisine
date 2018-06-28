package de.adesso.maitredecuisine.imagerecognition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tensorflow.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Führt die Bilderkennung durch
 */
@Service
public class RecognitionService {

    private static final int IMAGE_WIDTH=299;
    private static final int IMAGE_HEIGHT=299;
    private static final float IMAGE_MEAN=0.0f;
    private static final float IMAGE_SCALE=255.0f;

    private static final float QUALITY_CUTOFF = 0.01f;
    private static final int MAX_RESULTS = 10;


    private final GraphModel graphModel;
    private final LabelService labelService;

    @Autowired
    public RecognitionService(GraphModel graphModel, LabelService labelService) {
        this.graphModel = graphModel;
        this.labelService = labelService;
    }

    /**
     * Versucht Gegenstände im übergebenen Bild zu erkennen
     * @param image Das Bild in Binärform (Bildformate müssen noch geklärt werden)
     * @return Eine Liste mit erkannten Gegenstandsnamen
     */
    public List<RecognitionResult> recognize(byte[] image) {
        Tensor<Float> imageTensor = normalizeImage(image);
        return detectImage(imageTensor);
    }


    Tensor<Float> normalizeImage(byte[] imageData) {
        Graph graph = new Graph();
        GraphBuilder b = new GraphBuilder(graph);
        Output<String> input = b.constant("input", imageData);
        final Output<Float> output = b.div(
                b.sub(
                        b.resizeBilinear(
                                b.expandDims(
                                        b.cast(b.decodeJpeg(input, 3), Float.class),
                                        b.constant("make_batch", 0)),
                                b.constant("size", new int[]{IMAGE_HEIGHT, IMAGE_WIDTH})),
                        b.constant("mean", IMAGE_MEAN)),
                b.constant("scale", IMAGE_SCALE));
        try (Session s = new Session(graph)) {
            return s.runner().fetch(output.op().name()).run().get(0).expect(Float.class);
        }
    }

    List<RecognitionResult> detectImage(Tensor<Float> image) {
        try ( Session session = new Session(graphModel.getGraph())) {
            List<Tensor<?>> result = session.runner().feed("input",image).fetch("InceptionV3/Predictions/Reshape_1").run();
            Tensor<Float> t = (Tensor<Float>)result.get(0);
            float[][] matrix = new float[(int)t.shape()[0]][(int)t.shape()[1]];
            t.copyTo(matrix);
            List<Prediction> predictions = extractPredictions(matrix[0]);
            return predictions.stream().map(labelService::createResult).collect(Collectors.toList());
        }
    }

    List<Prediction> extractPredictions(float[] detectionResult) {
        List<Prediction> result = new ArrayList<>();
        for (int i = 0; i < detectionResult.length; i++) {
            if (detectionResult[i] > QUALITY_CUTOFF) {
                result.add( new Prediction(i,detectionResult[i]));
            }
        }
        Collections.sort(result);
        if (result.size() > MAX_RESULTS) {
            return result.subList(0,MAX_RESULTS-1);
        }
        return result;
    }

}
