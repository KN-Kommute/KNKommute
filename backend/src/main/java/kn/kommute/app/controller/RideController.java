package kn.kommute.app.controller;

import kn.kommute.app.dto.ParticipationDTO;
import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.ParticipationMapper;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Participation;
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
import kn.kommute.app.service.ParticipationService;

import java.util.List;

@RestController
@RequestMapping("api/rides")
public class RideController {
    @Autowired
    private final RideService rideService;
    @Autowired
    private final RideMapper rideMapper;
    @Autowired
    private final ParticipationMapper participationMapper;
    @Autowired
    private ParticipationService participationService;

    public RideController(RideService rideService, RideMapper rideMapper, ParticipationMapper participationMapper) {
        this.rideService = rideService;
        this.rideMapper = rideMapper;
        this.participationMapper = participationMapper;
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

     @PostMapping("/{rideId}/participation")
     public ResponseEntity<ParticipationDTO> createParticipation(
            @AuthenticationPrincipal User user,
            @PathVariable Long rideId,
            @RequestBody ParticipationDTO request
    ) {
        ParticipationDTO dto = participationService.createParticipation(
                user.getId(),
                rideId,
                request.getPickupLocation(),
                request.getPickupTime()
        );
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/{rideId}/participation/{participationId}/accept")
    public ResponseEntity<ParticipationDTO> acceptParticipation(@AuthenticationPrincipal User user, @PathVariable Long rideId, @PathVariable Long participationId) {
        Participation participation = participationService.acceptParticipation(rideId, participationId, user.getId());
        ParticipationDTO dto = participationMapper.toDTO(participation);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{rideId}/participation/{participationId}/reject")
    public ResponseEntity<Void> rejectParticipation(@AuthenticationPrincipal User rideOwner, @PathVariable Long rideId, @PathVariable Long participationId
    ) {
        participationService.rejectParticipation(participationId, rideOwner.getId());
        return ResponseEntity.ok().build();
}

    @PostMapping("/{rideId}/participation/user/{userId}/cancel")
    public ResponseEntity<Void> cancelParticipation(@AuthenticationPrincipal User rideOwner, @PathVariable Long rideId, @PathVariable Long userId
    ) {
        participationService.cancelParticipation(rideId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{rideId}/participations")
    public ResponseEntity<List<ParticipationDTO>> listParticipationOfRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(participationService.listByRide(rideId));
    }

    @GetMapping("/participations/{userId}")
    public ResponseEntity<List<ParticipationDTO>> listParticipationOfUser(@PathVariable Long userId) {
        return ResponseEntity.ok(participationService.listByUser(userId));
    }


}
