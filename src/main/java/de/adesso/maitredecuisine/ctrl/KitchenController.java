package de.adesso.maitredecuisine.ctrl;

import de.adesso.maitredecuisine.dto.ImagesDTO;
import de.adesso.maitredecuisine.dto.RecipesDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KitchenController {

    @PostMapping("/v1/recognitions")
    /**
     * Analysiere die übergebenen Bilder und gib die erkannten Objekte inkl. der Erkennungsqualität zurück.
     */
    public ResponseEntity<ImagesDTO> getRecognitions(@RequestBody() ImagesDTO images) {
        ImagesDTO recognitions = new ImagesDTO();
        ResponseEntity<ImagesDTO> response = new ResponseEntity<>(recognitions, HttpStatus.OK);

        return response;
    }

    @PostMapping("/v1/recipes")
    /*
     * Analysiere die übergebenen Bilder und gib Rezept-Vorschläge zurück.
     */
    public ResponseEntity<RecipesDTO> getReceipes(@RequestBody() ImagesDTO images) {
        RecipesDTO recipes = new RecipesDTO();
        ResponseEntity<RecipesDTO> response = new ResponseEntity<>(recipes, HttpStatus.OK);

        return response;
    }
}
