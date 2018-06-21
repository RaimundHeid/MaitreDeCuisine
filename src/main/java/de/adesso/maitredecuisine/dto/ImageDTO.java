package de.adesso.maitredecuisine.dto;

import lombok.Data;
import org.springframework.util.MimeType;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ImageDTO {
    @NotNull
    private String id;
    @NotNull
    private MimeType mimeType;
    private byte[] content;
    private List<RecognizedObjectDTO> recognizedObjects;

    public void addRecognizedObjects(RecognizedObjectDTO recognizedObjectDTO) {
        if (recognizedObjects == null) {
            recognizedObjects = new ArrayList<>();
        }
        recognizedObjects.add(recognizedObjectDTO);
    }

}
