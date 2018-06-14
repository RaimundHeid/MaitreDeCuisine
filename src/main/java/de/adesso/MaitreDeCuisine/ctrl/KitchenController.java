package de.adesso.MaitreDeCuisine.ctrl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class KitchenController {

    @GetMapping("/recipes/{ingredients}")
    public ResponseEntity<String> getReceipes(@PathParam(value = "ingredients") String ingredients) {
        ResponseEntity<String> response = new ResponseEntity<>("So you own " + ingredients + "?", HttpStatus.OK);

        return response;
    }
}
