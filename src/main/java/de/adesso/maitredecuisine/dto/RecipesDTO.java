package de.adesso.maitredecuisine.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipesDTO {
    private List<RecipeDTO> recipes;
}
