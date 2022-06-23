package susu.kursach.webpage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import susu.kursach.webpage.service.PlacementServiceMetaService;

@RestController
@RequestMapping("/api/servicemeta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin("http://localhost:8081/")
public class ServiceMetaController {

    private final PlacementServiceMetaService placementServiceMetaService;

    @GetMapping
    public ResponseEntity<?> getAllServiceMetas(){
        var meta = placementServiceMetaService.getAllServicesMeta();

        return ResponseEntity.ok().body(meta);
    }
}
