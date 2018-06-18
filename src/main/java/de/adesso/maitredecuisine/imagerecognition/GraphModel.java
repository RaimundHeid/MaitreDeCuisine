package de.adesso.maitredecuisine.imagerecognition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class GraphModel {

    @Value("classpath:/model/oidv2")
    private String modelResourcePath;

    @Value("${maitre.loadmodel:true}")
    private boolean loadModel;

    private SavedModelBundle savedModelBundle;

    private final ResourceLoader resourceLoader;

    @Autowired
    public GraphModel(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Graph getGraph() {
        return savedModelBundle.graph();
    }

    String modelFileName() throws IOException {
        return resourceLoader.getResource(modelResourcePath).getFile().getAbsolutePath();
    }

    @PostConstruct
    public void load() throws IOException {
        if ( loadModel ) {
            // Das Laden des Models dauert recht lange. Damit sich nicht jeder Test damit herumplagen muss,
            // ist das Laden des Models konfigurierbar
            String fileName = modelFileName();
            savedModelBundle = SavedModelBundle.load(fileName, "serve");
        }
    }


}
