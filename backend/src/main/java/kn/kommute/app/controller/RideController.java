package kn.kommute.app.controller;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Ride;
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
    private RideMapper rideMapper;


    @PostMapping("/create")
    public ResponseEntity<RideDTO> createRide(@AuthenticationPrincipal User user, @RequestBody RideDTO dto) {
        Ride createdRide = rideService.createRide(user, dto);
        RideDTO responseDto = rideMapper.toSummaryDTO(createdRide);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }



    @GetMapping
    public ResponseEntity<List<RideDTO>> listRides() {
        return ResponseEntity.ok(rideService.listRides());
    }

    @PostMapping("/{rideId}/participate")
    public ResponseEntity<?> participate(@AuthenticationPrincipal User user, @PathVariable Long rideId) {
        RideDTO response = rideService.participate(user, rideId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ride not found or full");
        }
        return ResponseEntity.ok(response);
    }
}
