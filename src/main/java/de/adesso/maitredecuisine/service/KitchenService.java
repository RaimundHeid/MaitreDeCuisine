package de.adesso.maitredecuisine.service;

import de.adesso.maitredecuisine.client.edadam.model.Recipe;
import de.adesso.maitredecuisine.dto.*;
import de.adesso.maitredecuisine.imagerecognition.RecognitionResult;
import de.adesso.maitredecuisine.imagerecognition.RecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class KitchenService {

    @Value("${maitre.ingredients.threshold}")
    private double ingredientsThreshold;


    private final RecognitionService recognitionService;

    @Autowired
    public KitchenService(RecognitionService recognitionService) {
        this.recognitionService = recognitionService;
    }

    /**
     * Erzeugt die Rezept-Antwort aus der Antwort der Rezept Datenbank
     * @param recipeList die Liste der Rezepte aus der Datenbank
     * @return die Rezept-Antwort
     */
    public RecipesDTO convertRecipes(List<Recipe> recipeList) {
        RecipesDTO recipes = new RecipesDTO();
        if (!CollectionUtils.isEmpty(recipeList)) {

            recipeList.stream().forEach(recipe -> {
                RecipeDTO recipeDTO = new RecipeDTO();
                recipeDTO.setLabel(recipe.getLabel());
                recipeDTO.setImageUrl(recipe.getImage());
                recipeDTO.setUrl(recipe.getUrl());
                recipeDTO.setIngredientLines(recipe.getIngredientLines());
                recipes.addRecipe(recipeDTO);
            });
        }
        return recipes;
    }

    /**
     * Fügt den Bildern die erkannten Zutaten hinzu.
     * @param images die Bilddaten
     */
    public void enrichWithIngredient(ImagesDTO images) {
        if (!CollectionUtils.isEmpty(images.getImages())) {
            for (ImageDTO image : images.getImages()) {
                List<RecognitionResult> recognize = recognitionService.recognize(image.getContent());
                if (!CollectionUtils.isEmpty(recognize)) {
                    image.setRecognizedObjects(null);
                    recognize.stream().forEach(recognitionResult -> {
                        RecognizedObjectDTO recognizedObjectDTO = new RecognizedObjectDTO();
                        recognizedObjectDTO.setLabel(recognitionResult.getLabel());
                        recognizedObjectDTO.setQuality(recognitionResult.getQuality());
                        image.addRecognizedObjects(recognizedObjectDTO);
                    });
                }
            }
        }
    }

    /**
     * Sammelt die Zutaten aller Bilder, die den konfigurierten Schwellwert überschreiten
     * @param images die Bilder
     * @return die Menge der Zutaten
     */
    public Set<String> collectAllIngredients(ImagesDTO images) {
        Set<String> allIngredients = new HashSet<>();
        images.getImages().stream().filter(imageDTO -> !CollectionUtils.isEmpty(imageDTO.getRecognizedObjects()))
                .forEach(imageDTO -> imageDTO.getRecognizedObjects().stream()
                        .filter(recognizedObjectDTO -> recognizedObjectDTO.getQuality() >= ingredientsThreshold)
                        .forEach(recognizedObjectDTO -> allIngredients.add(recognizedObjectDTO.getLabel())));
        return allIngredients;
    }

}
