package de.adesso.maitredecuisine.client.edadam.model;

import lombok.Data;

import java.util.List;

/**
 * The query result
 */
@Data
public class EdamamResult {

    /**
     * Query text, as submitted
     */
    private String q;

    /**
     * First result index, as submitted
     */
    private Integer from;

    /**
     * Last result index, as submitted
     */
    private Integer to;

    /**
     * 	Number of results found
     */
    private Integer count;

    /**
     * More that the maximum allowed number of results found
     */
    private Boolean more;

    /**
     * Matching results (Hit objects)
     */
    private List<Hit> hits;

}
