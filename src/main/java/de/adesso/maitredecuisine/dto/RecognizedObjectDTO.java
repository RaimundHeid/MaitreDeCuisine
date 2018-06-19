package de.adesso.maitredecuisine.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RecognizedObjectDTO {
    @NotNull
    String label;
    double quality;
}
