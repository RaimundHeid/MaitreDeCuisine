package de.adesso.maitredecuisine.imagerecognition;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.tensorflow.Graph;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Lädt das Neuronale Netz aus einer Datei in den Speicher
 */
@Component
public class GraphModel {

    @Value("classpath:/model/imagenet/saved_model.pb")
    private String modelResourcePath;

    @Value("${maitre.loadmodel:true}")
    private boolean loadModel;

    private Graph graph;

    private final ResourceLoader resourceLoader;

    @Autowired
    public GraphModel(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Graph getGraph() {
        return graph;
    }

    String modelFileName() throws IOException {
        return resourceLoader.getResource(modelResourcePath).getFile().getAbsolutePath();
    }


    @PostConstruct
    public void loadGraph() throws IOException {
        graph = new Graph();
        try (InputStream in = resourceLoader.getResource(modelResourcePath).getInputStream()) {
            byte[] graphDef =  IOUtils.toByteArray(in);
            graph.importGraphDef(graphDef);
        }

    }


}
