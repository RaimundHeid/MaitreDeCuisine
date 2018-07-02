package de.adesso.maitredecuisine.imagerecognition;

import de.adesso.maitredecuisine.MaitreDeCuisineApplication;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { MaitreDeCuisineApplication.class })
public class RecognitionServiceTest {

    @Autowired
    private RecognitionService service;

    @Autowired
    private GraphModel graphModel;

    @Autowired
    private ResourceLoader resourceLoader;

    private String[] imageNames = {"veg1", "veg2", "veg3", "bier","erdbeere","tomate"};

    @Test
    public void testPrepareImage() throws IOException {
        byte[] imageData = loadImage("veg1.jpg");
        Tensor<Float> convertedImage = service.normalizeImage(imageData);
        assertNotNull(convertedImage);
    }

    @Test
    public void testDetectImage() throws IOException {
        ReflectionTestUtils.setField(graphModel,"loadModel",true);
        graphModel.loadGraph();
        byte[] imageData = loadImage("veg1.jpg");
        Tensor<Float> convertedImage = service.normalizeImage(imageData);
        assertNotNull(convertedImage);
        List<RecognitionResult> result = service.detectImage(convertedImage);
        assertNotNull(result);
    }

    @Test
    public void testDetectImageList() throws IOException {
        ReflectionTestUtils.setField(graphModel,"loadModel",true);
        graphModel.loadGraph();
        for (String name : imageNames) {
            byte[] imageData = loadImage(name + ".jpg");
            Tensor<Float> convertedImage = service.normalizeImage(imageData);
            List<RecognitionResult> result = service.detectImage(convertedImage);
            writeResult(name,result);
        }
    }

    private byte[] loadImage(String name) throws IOException {
        try (InputStream in = resourceLoader.getResource("classpath:/images/" + name).getInputStream()) {
            return IOUtils.toByteArray(in);
        }
    }

    private void writeResult(String name, List<RecognitionResult> results) {
        for (RecognitionResult r : results) {
            System.out.println(name + ": " + r.getLabel() + "   " + r.getQuality());
        }
    }
}