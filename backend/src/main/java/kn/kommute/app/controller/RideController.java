package kn.kommute.app.controller;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.model.User;
import kn.kommute.app.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/create")
    public ResponseEntity<String> createRide(@AuthenticationPrincipal User user, @RequestBody RideDTO dto) {
        rideService.createRide(user, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ride created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listRides() {
        return ResponseEntity.ok(rideService.listRides());
    }

    @PostMapping("/{rideId}/participate")
    public ResponseEntity<?> participate(@AuthenticationPrincipal User user, @PathVariable Long rideId) {
        try {
            Map<String, Object> response = rideService.participate(user, rideId);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ride not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Maximum number of participants reached");
        }
    }
}
