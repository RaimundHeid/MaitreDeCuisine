package de.adesso.maitredecuisine.imagerecognition;


import java.util.Objects;

/**
 * Rückgabwert nach einer Bilderkennung. Es besteht aus einem Label (der Name eines erkannten Gegenstands. z.B. Tomato oder Cucumber) und
 * einem Wert, der angibt, wie sicher das Label erkannt wurde. 1.0 entspricht dabei 100%.
 */
public class RecognitionResult implements Comparable<RecognitionResult> {

    private final String label;
    private final float quality;


    public RecognitionResult( String label, float quality) {
        this.label = label;
        this.quality = quality;
    }

    public String getLabel() {
        return label;
    }

    public float getQuality() {
        return quality;
    }

    @Override
    public int compareTo(RecognitionResult other) {
        return Float.compare(other.quality, quality);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecognitionResult that = (RecognitionResult) o;
        return Double.compare(that.quality, quality) == 0 && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, quality);
    }

    @Override
    public String toString() {
        return "RecognitionResult{" + "label='" + label + '\'' + ", quality=" + quality + '}';
    }
}
