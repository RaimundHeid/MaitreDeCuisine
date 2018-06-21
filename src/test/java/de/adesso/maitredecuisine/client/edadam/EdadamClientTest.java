package de.adesso.maitredecuisine.client.edadam;

import de.adesso.maitredecuisine.client.edadam.model.Recipe;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Tests for the {@link EdamamClient}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EdadamClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(EdadamClientTest.class);


    @Autowired
    private EdamamClient client;

    @Test
    public void searchNoIngredients() {
        try {
            client.search(null);
            fail("IllegalArgumentException expected.");
        }
        catch (IllegalArgumentException e) {
        }
    }

    @Test
    @Ignore
    public void search() {

        List<String> ingredients = new ArrayList<>();
        ingredients.add("tomatoe");
        ingredients.add("pasta");
        List<Recipe> search = client.search(ingredients);

        for(Recipe recipe : search) {
            LOG.debug(recipe.toString());
        }



    }
}