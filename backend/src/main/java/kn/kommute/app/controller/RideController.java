package kn.kommute.app.controller;

import kn.kommute.app.dto.ParticipationDTO;
import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.ParticipationMapper;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Participation;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.service.ParticipationService;
import kn.kommute.app.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private ParticipationMapper participationMapper;

    @Autowired
    private RideMapper rideMapper;

    
    @PostMapping("/create")
    public ResponseEntity<Ride> createRide(@AuthenticationPrincipal User user, @RequestBody Ride ride) {
        Ride createdRide = rideService.createRide(ride, user.getId());
        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RideDTO>> listRides() {
        return ResponseEntity.ok(rideService.listRides());
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> deleteRide(@AuthenticationPrincipal User user, @PathVariable Long rideId) {
        rideService.deleteById(rideId);
        return ResponseEntity.noContent().build();
    }


    // Participation endpoints

    @PostMapping("/{rideId}/participations")
    public ResponseEntity<ParticipationDTO> createParticipation(@AuthenticationPrincipal User user, @PathVariable Long rideId, @RequestBody ParticipationDTO request
    ) {
        ParticipationDTO dto = participationService.createParticipation(
                user.getId(), rideId, request.getPickupLocation(), request.getPickupTime()
        );

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/{rideId}/participations/{participationId}/accept")
    public ResponseEntity<ParticipationDTO> acceptParticipation(@PathVariable Long participationId) {
        Participation participation = participationService.acceptParticipation(participationId);
        return ResponseEntity.ok(participationMapper.toDTO(participation));
    }

    @PostMapping("/{rideId}/participations/{participationId}/reject")
    public ResponseEntity<Void> rejectParticipation(
            @AuthenticationPrincipal User rideOwner,
            @PathVariable Long rideId,
            @PathVariable Long participationId
    ) {
        participationService.rejectParticipation(participationId, rideOwner.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{rideId}/participations")
    public ResponseEntity<List<ParticipationDTO>> listParticipations(
            @PathVariable Long rideId
    ) {
        List<Participation> participations = participationService.listByRide(rideId);
        List<ParticipationDTO> dtos = participations.stream()
                .map(participationMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}