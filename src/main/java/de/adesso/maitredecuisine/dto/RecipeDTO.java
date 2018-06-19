package de.adesso.maitredecuisine.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {
    private String url;
    private String label;
    private ImageDTO image;
    private List<String> ingredientLines;
}
