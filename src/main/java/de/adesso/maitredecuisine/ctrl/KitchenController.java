package de.adesso.maitredecuisine.ctrl;

import de.adesso.maitredecuisine.client.edadam.EdamamClient;
import de.adesso.maitredecuisine.client.edadam.model.Recipe;
import de.adesso.maitredecuisine.dto.*;
import de.adesso.maitredecuisine.imagerecognition.RecognitionService;
import de.adesso.maitredecuisine.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KitchenController {

    private final EdamamClient edamamClient;

    private final KitchenService kitchenService;


    @Autowired
    public KitchenController(EdamamClient edamamClient, KitchenService kitchenService) {
        this.edamamClient = edamamClient;
        this.kitchenService = kitchenService;
    }

    @PostMapping("/v1/recognitions")
    /**
     * Analysiere die übergebenen Bilder und gib die erkannten Objekte inkl. der Erkennungsqualität zurück.
     */
    public ResponseEntity<ImagesDTO> getRecognitions(@RequestBody() ImagesDTO images) {

        kitchenService.enrichWithIngredient(images);

        return new ResponseEntity<>(images, HttpStatus.OK);
    }


    @PostMapping("/v1/recipes")
    /**
     * Analysiere die übergebenen Bilder und gib Rezept-Vorschläge zurück.
     */
    public ResponseEntity<RecipesDTO> getReceipes(@RequestBody() ImagesDTO images) {

        kitchenService.enrichWithIngredient(images);
        List<Recipe> recipeList = edamamClient.search(kitchenService.collectAllIngredients(images));
        RecipesDTO recipes = kitchenService.convertRecipes(recipeList);

        return new ResponseEntity<>(recipes, HttpStatus.OK);

    }


}
