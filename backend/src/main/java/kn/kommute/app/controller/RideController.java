package kn.kommute.app.controller;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {
    @Autowired
    private final RideService rideService;
    @Autowired
    private final RideMapper rideMapper;

    public RideController(RideService rideService, RideMapper rideMapper) {
        this.rideService = rideService;
        this.rideMapper = rideMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
        // Pega o usuário autenticado do SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = (User) authentication.getPrincipal();

        // Cria a Ride associando o usuário autenticado
        Ride createdRide = rideService.createRide(ride, user.getId());

        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
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
    public ResponseEntity deleteRide(@AuthenticationPrincipal User user, @PathVariable Long rideId) {
        rideService.deleteById(rideId);
        return ResponseEntity.noContent().build();
    }



}
