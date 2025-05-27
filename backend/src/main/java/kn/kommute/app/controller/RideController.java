package kn.kommute.app.controller;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;
    private final RideMapper rideMapper;

    public RideController(RideService rideService, RideMapper rideMapper) {
        this.rideService = rideService;
        this.rideMapper = rideMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<RideDTO> createRide(@AuthenticationPrincipal User user, @RequestBody RideDTO dto) {
        Ride createdRide = rideService.createRide(user, dto);
        RideDTO responseDto = rideMapper.toRideDTO(createdRide);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<RideDTO>> listRides() {
        return ResponseEntity.ok(rideService.listRides());
    }

    @PostMapping("/{rideId}/participate")
    public ResponseEntity<RideDTO> participate(@AuthenticationPrincipal User user, @PathVariable Long rideId) {
        RideDTO response = rideService.participate(user, rideId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{rideId}")
    public void deleteRide(@AuthenticationPrincipal User user, @PathVariable Long rideId) {
        rideService.deleteById(rideId);
    }
}
