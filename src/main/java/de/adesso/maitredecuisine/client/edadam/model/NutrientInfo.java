package de.adesso.maitredecuisine.client.edadam.model;

import lombok.Data;

@Data
public class NutrientInfo {

    /**
     * Ontology identifier
     */
    private String uri;
    /**
     * Display label
     */
    private String label;

    /**
     * Quantity of specified units
     */
    private Float quantity;

    /**
     * Units
     */
    private String unit;

}
