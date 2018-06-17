package de.adesso.maitredecuisine.client.edadam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Tests for the {@link EdamamClient}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EdadamClientTest {

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
    public void search() {

        List<String> ingredients = new ArrayList<>();
        ingredients.add("tomatoe");
        ingredients.add("pasta");
        client.search(ingredients);

    }
}