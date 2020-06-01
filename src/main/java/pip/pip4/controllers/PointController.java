package pip.pip4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pip.pip4.dtos.PointDTO;
import pip.pip4.mbeans.AverageTimeBetweenClicksCounter;
import pip.pip4.mbeans.PointsCounter;
import pip.pip4.services.PointService;

import java.util.Date;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class PointController {

    private final PointService pointService;
    private final PointsCounter pointsCounter;
    private final AverageTimeBetweenClicksCounter clicksCounter;

    @Autowired
    public PointController(PointService pointService, PointsCounter pointsCounter, AverageTimeBetweenClicksCounter clicksCounter) {
        this.pointService = pointService;
        this.pointsCounter = pointsCounter;
        this.clicksCounter = clicksCounter;
    }

    @GetMapping("/points")
    ResponseEntity<?> getUserPoints(Authentication authentication) {
        try {
            String username = authentication.getName();
            System.out.println("request get /points by " + username);
            return ResponseEntity.ok(pointService.getPoints(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to load user points");
        }
    }

    @PostMapping("/points")
    ResponseEntity<String> addPoint(Authentication authentication, @Validated @RequestBody PointDTO point) {
        try {
            long requestTime = new Date().getTime();
            String username = authentication.getName();
            System.out.println("request post /points by " + username);
            pointService.addPoint(username, point);
            pointsCounter.incrementUserPointsCounter(username, point.getResult());
            clicksCounter.updateAverageTimeBetweenClicks(requestTime);
            return ResponseEntity.ok("Point added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
        }
    }
}
