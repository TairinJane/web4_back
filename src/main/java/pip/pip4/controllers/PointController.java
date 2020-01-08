package pip.pip4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pip.pip4.dtos.PointDTO;
import pip.pip4.services.PointService;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/points")
    ResponseEntity<?> getUserPoints(Authentication authentication) {
        try {
            String username = authentication.getName();
            System.out.println("request get /points by " + username);
            return ResponseEntity.ok(pointService.getPoints(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }
    }

    @PostMapping("/points")
    ResponseEntity<String> addPoint(Authentication authentication, @Validated @RequestBody PointDTO point) {
        try {
            String username = authentication.getName();
            System.out.println("request post /points by " + username);
            pointService.addPoint(username, point);
            return ResponseEntity.ok("Point added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
        }
    }
}
