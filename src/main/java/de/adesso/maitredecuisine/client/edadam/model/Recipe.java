package de.adesso.maitredecuisine.client.edadam.model;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {

    /**
     * Ontology identifier
     */
    private String uri;

    /**
     * Recipe title
     */
    private String label;

    /**
     * Image URL
     */
    private String image;

    /**
     * Source site identifier
     */
    private String source;

    /**
     * Original recipe URL
     */
    private String url;
    /**
     * Number of servings
     */
    private String yield;
    /**
     * Total energy, kcal
     */
    private Float calories;
    /**
     * Total weight, g
     */
    private Float totalWeight;

    /**
     * Ingredients list as Text
     */
    private List<String> ingredientLines;

    /**
     * Ingredients list
     */
    private List<Ingredient> ingredients;

    /**
     * Total nutrients for the entire recipe
     */
    // private List<NutrientInfo> totalNutrients;
    /**
     * % daily value for the entire recipe
     */
    // private List<NutrientInfo> totalDaily;
    /**
     * Diet labels: “balanced”, “high-protein”, “high-fiber”, “low-fat”, “low-carb”, “low-sodium” (labels are per serving)
     */
    private List<String> dietLabels;
    /**
     * Health labels: “vegan”, “vegetarian”, “paleo”, “dairy-free”, “gluten-free”, “wheat-free”, “fat-free”, “low-sugar”, “egg-free”,
     * “peanut-free”, “tree-nut-free”, “soy-free”, “fish-free”, “shellfish-free” (labels are per serving)
     */
    private List<String> healthLabels;

}
