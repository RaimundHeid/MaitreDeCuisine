package de.adesso.maitredecuisine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adesso.maitredecuisine.MaitreDeCuisineApplication;
import de.adesso.maitredecuisine.client.edadam.model.Recipe;
import de.adesso.maitredecuisine.dto.ImagesDTO;
import de.adesso.maitredecuisine.dto.RecipesDTO;
import de.adesso.maitredecuisine.imagerecognition.RecognitionResult;
import de.adesso.maitredecuisine.imagerecognition.RecognitionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { MaitreDeCuisineApplication.class })
public class KitchenServiceTest {

    private static final double DELTA = 0.0001;

    @Autowired
    private KitchenService service;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecognitionService recognitionService;


    @Test
    public void convertEmptyRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        RecipesDTO recipesDTO = service.convertRecipes(recipeList);
        assertNotNull(recipesDTO);
        assertNull(recipesDTO.getRecipes());
    }


    @Test
    public void convertNullRecipes() {
        RecipesDTO recipesDTO = service.convertRecipes(null);
        assertNotNull(recipesDTO);
        assertNull(recipesDTO.getRecipes());
    }

        @Test
    public void convertRecipes() {

        List<Recipe> recipeList = new ArrayList<>();

            Recipe recipe1 = new Recipe();
            recipe1.setLabel("Recipe-1");
            recipe1.setImage("https://image1.png");
            recipe1.setUrl("https://recipe1.html");
            recipe1.setIngredientLines(Arrays.asList("Ingredient1-1", "Ingredient1-2", "Ingredient1-3"));
            recipeList.add(recipe1);

            Recipe recipe2 = new Recipe();
            recipe2.setLabel("Recipe-2");
            recipe2.setImage("https://image2.png");
            recipe2.setUrl("https://recipe2.html");
            recipe2.setIngredientLines(Arrays.asList("Ingredient2-1", "Ingredient2-2"));
            recipeList.add(recipe2);


            RecipesDTO recipesDTO = service.convertRecipes(recipeList);

            assertNotNull(recipesDTO);
            assertEquals(2, recipesDTO.getRecipes().size());

            assertEquals("Recipe-1", recipesDTO.getRecipes().get(0).getLabel());
            assertEquals("https://image1.png", recipesDTO.getRecipes().get(0).getImageUrl());
            assertEquals("https://recipe1.html", recipesDTO.getRecipes().get(0).getUrl());
            assertEquals(3, recipesDTO.getRecipes().get(0).getIngredientLines().size());

            assertEquals("Recipe-2", recipesDTO.getRecipes().get(1).getLabel());
            assertEquals("https://image2.png", recipesDTO.getRecipes().get(1).getImageUrl());
            assertEquals("https://recipe2.html", recipesDTO.getRecipes().get(1).getUrl());
            assertEquals(2, recipesDTO.getRecipes().get(1).getIngredientLines().size());
        }


    @Test
    public void enrichWithIngredient() throws IOException {

        Mockito.when(recognitionService.recognize("cont-1".getBytes()))
                .thenReturn(Arrays.asList( new RecognitionResult( "Gurke", 0.75f),  new RecognitionResult( "Tomate", 0.9f)));
        Mockito.when(recognitionService.recognize("cont-2".getBytes()))
                .thenReturn(Arrays.asList( new RecognitionResult( "Brot", 0.5f)));

        ImagesDTO images = objectMapper.readValue(
                resourceLoader.getResource("classpath:data/emptyImagesDTO.json").getInputStream(), ImagesDTO.class);


        service.enrichWithIngredient(images);

        assertEquals(2, images.getImages().get(0).getRecognizedObjects().size());
        assertEquals("Gurke", images.getImages().get(0).getRecognizedObjects().get(0).getLabel());
        assertEquals(0.75, images.getImages().get(0).getRecognizedObjects().get(0).getQuality(), DELTA);
        assertEquals("Tomate", images.getImages().get(0).getRecognizedObjects().get(1).getLabel());
        assertEquals(0.9, images.getImages().get(0).getRecognizedObjects().get(1).getQuality(), DELTA);
        assertEquals(1, images.getImages().get(1).getRecognizedObjects().size());
        assertEquals("Brot", images.getImages().get(1).getRecognizedObjects().get(0).getLabel());
        assertEquals(0.5, images.getImages().get(1).getRecognizedObjects().get(0).getQuality(), DELTA);

    }

    @Test
    public void collectAllIngredients() throws IOException {
        ImagesDTO imagesDTO = objectMapper.readValue(
                resourceLoader.getResource("classpath:data/imagesDTO.json").getInputStream(), ImagesDTO.class);

        Set<String> ingredients = service.collectAllIngredients(imagesDTO);

        assertEquals(4, ingredients.size());
        assertTrue(ingredients.contains("Tomate"));
        assertTrue(ingredients.contains("Gurke"));
        assertTrue(ingredients.contains("Brot"));
        assertTrue(ingredients.contains("Zucker"));
    }
}