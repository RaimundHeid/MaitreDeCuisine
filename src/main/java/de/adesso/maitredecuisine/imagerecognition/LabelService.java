package de.adesso.maitredecuisine.imagerecognition;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Wandelt die Indices des Neuralen Netzes in Labels um
 */
@Component
public class LabelService {

    @Value("classpath:/model/imagenet/imagenet_slim_labels.txt")
    private String labelFile;

    private final ResourceLoader resourceLoader;

    private List<String> labels = new ArrayList<>();

    @Autowired
    public LabelService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String label(int index) {
        return labels.get(index);
    }

    public RecognitionResult createResult(Prediction prediction) {
        return new RecognitionResult( label(prediction.getIndex()), prediction.getQuality());
    }

    @PostConstruct
    public void loadLabels() throws IOException {
        try (InputStream in = resourceLoader.getResource(labelFile).getInputStream()) {
            labels.addAll(IOUtils.readLines(in,StandardCharsets.UTF_8));
        }
    }

}
