package de.adesso.maitredecuisine.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {
    private String url;
    private String label;
    private String imageUrl;
    private List<String> ingredientLines;
}
