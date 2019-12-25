package pip.pip4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pip.pip4.dtos.PointDTO;
import pip.pip4.services.PointService;

import java.util.List;

@Controller
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:3000")
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/points")
    List<PointDTO> getUserPoints(@RequestHeader(value = "Authorization") String tokenHeader) {
        return pointService.getPoints(tokenHeader);
    }

    @PostMapping("/points")
    ResponseEntity<String> addPoint(@RequestHeader(value = "Authorization") String tokenHeader, @Validated @RequestBody PointDTO point) {
        pointService.addPoint(tokenHeader, point);
        return ResponseEntity.ok("Point added");
    }
}
