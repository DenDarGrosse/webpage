package susu.kursach.webpage.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susu.kursach.webpage.service.CompositePlacementServiceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/api/composite")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin("http://localhost:8081/")
public class CompositeController {

    private final CompositePlacementServiceFactory compositePlacementServiceFactory;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<?> createComposite(@RequestParam String type, @RequestBody HashMap<String, String> params) {
        if (!isValid(params.values())) {
            throw new Exception("Not valid data");
        }

        var compositeGenerationService = compositePlacementServiceFactory.getService(type);
        compositeGenerationService.createComposite(params);

        File file = new File("results.lgw");
        if (!file.exists()) {
            throw new Exception("Unexpected exception");
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));


        var contentType = "application/octet-stream";
        var headerValue = "attachment;";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    private boolean isValid(Collection<String> params){
        return params.stream().allMatch(this::isNumeric);
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
            return d >= 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
