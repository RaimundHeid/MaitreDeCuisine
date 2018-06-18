package de.adesso.maitredecuisine.imagerecognition;


import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Führt die Bilderkennung durch
 */
@Service
public class RecognitionService {

    /**
     * Versucht Gegenstände im übergebenen Bild zu erkennen
     * @param image Das Bild in Binärform (Bildformate müssen noch geklärt werden)
     * @return Eine Liste mit erkannten Gegenstandsnamen
     */
    public List<RecognitionResult> recognize(byte[] image) {
        return Collections.singletonList(new RecognitionResult("Strawberry", 0.7));
    }


}
