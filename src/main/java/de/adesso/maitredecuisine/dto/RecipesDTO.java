package de.adesso.maitredecuisine.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipesDTO {
    private List<RecipeDTO> recipes;

    public void addRecipe(RecipeDTO recipe) {
        if (recipes == null) {
            recipes = new ArrayList<>();
        }
        recipes.add(recipe);
    }

}
