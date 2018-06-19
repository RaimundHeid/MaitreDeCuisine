package de.adesso.maitredecuisine.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ImagesDTO {
    @NotNull
    private List<ImageDTO> images;
}
