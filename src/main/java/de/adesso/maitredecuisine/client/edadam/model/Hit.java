package de.adesso.maitredecuisine.client.edadam.model;

import lombok.Data;

@Data
public class Hit {

    /**
     * Matching recipe
     */
    private Recipe recipe;

    /**
     * Is this recipe bookmarked by the searching user?
     */
    private boolean bookmarked;

    /**
     * Is this recipe bought by the searching user?
     */
    private boolean bought;

}
