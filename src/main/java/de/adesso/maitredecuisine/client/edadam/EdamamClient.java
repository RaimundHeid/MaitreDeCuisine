package de.adesso.maitredecuisine.client.edadam;

import de.adesso.maitredecuisine.client.edadam.model.EdamamResult;
import de.adesso.maitredecuisine.client.edadam.model.Hit;
import de.adesso.maitredecuisine.client.edadam.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Client for the <a href="https://developer.edamam.com/edamam-docs-recipe-api">Edamam-API</a>
 */
@Component
public class EdamamClient {

    private static final Logger LOG = LoggerFactory.getLogger(EdamamClient.class);

    @Value("${edamam.baseUrl}")
    private String baseUrl;

    @Value("${edamam.applicationId}")
    private String applicationId;

    @Value("${edamam.applicationKey}")
    private String applicationKey;


    /**
     * Search the edamam database for a recipt containing some ingredients
     *
     * @param ingredients the ingredients
     * @return the recipts
     */
    public List<Recipe> search(List<String> ingredients) {

        if ((ingredients == null) || (ingredients.isEmpty())) {
            throw new IllegalArgumentException("ingredients must not be empty.");
        }

        List<Recipe> resultList = new ArrayList<>();

        String queryString = createQueryString(ingredients);

        LOG.debug("search edadam for: {}", queryString);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("app_id", applicationId)
                .queryParam("app_key", applicationKey)
                .queryParam("q", queryString);


        RestTemplate restTemplate = new RestTemplate();
        try {
            EdamamResult result = restTemplate.getForObject(builder.toUriString(), EdamamResult.class);

            if (result != null) {
                LOG.debug(result.toString());
                for(Hit hit : result.getHits()) {
                    resultList.add(hit.getRecipe());
                }
            }

        }
        catch (RestClientException e) {
            LOG.error(String.format("failed to get recipes for %s", queryString), e);
        }


        return resultList;

    }

    private String createQueryString(List<String> ingredients) {
        StringBuilder queryString = new StringBuilder();
        for(int i = 0; i < ingredients.size(); i++) {
            if (i > 0) {
                queryString.append(",");
            }
            queryString.append(ingredients.get(i).trim());
        }
        return queryString.toString();
    }


}
