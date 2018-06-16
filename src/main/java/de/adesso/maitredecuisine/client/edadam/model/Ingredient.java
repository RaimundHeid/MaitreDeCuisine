package de.adesso.maitredecuisine.client.edadam.model;

import lombok.Data;

@Data
public class Ingredient {

    /**
     * Ontology identifier
     */
    private String uri;
    /**
     * Label
     */
    private String text;
    /**
     * Quantity of specified measure
     */
    private Float quantity;
    /**
     * Measure
     */
    private Measure measure;
    /**
     * Total weight, g
     */
    private Float weight;

    /**
     * Food
     */
    private Food food;

}
