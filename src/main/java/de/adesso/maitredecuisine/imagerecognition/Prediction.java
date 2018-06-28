package de.adesso.maitredecuisine.imagerecognition;

/**
 * Sammelt die Ausgangsdaten aus dem Neuralen Netz.
 * Predictions werden zu RecognitionResult weiterverarbeitet
 */
public class Prediction implements  Comparable<Prediction> {


    private final int index;
    private final float quality;

    public Prediction(int index, float quality) {
        this.index = index;
        this.quality = quality;
    }

    public int getIndex() {
        return index;
    }

    public float getQuality() {
        return quality;
    }

    @Override
    public int compareTo(Prediction o) {
        // umgekehrte Reihenfolge
        return Float.compare(o.quality,quality);
    }
}
