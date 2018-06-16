package de.adesso.maitredecuisine.client.edadam.model;

import lombok.Data;

@Data
public class Food {

    /**
     * Ontology identifier
     */
    private String uri;

    /**
     * Common name
     */
    private String label;
}
