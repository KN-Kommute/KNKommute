package kn.kommute.app.service;

import kn.kommute.app.dto.ParticipationDTO;
import kn.kommute.app.model.Participation;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.ParticipationRepository;
import kn.kommute.app.repository.RideRepository;
import kn.kommute.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    public ParticipationDTO createParticipation(Long userId, Long rideId, String pickupLocation, LocalDateTime pickupTime) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        User participant = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Participation participation = new Participation();
        participation.setRide(ride);
        participation.setUser(participant);
        participation.setPickupLocation(pickupLocation);
        participation.setPickupTime(pickupTime);

        Participation saved = participationRepository.save(participation);

        // Criação manual do DTO
        ParticipationDTO dto = new ParticipationDTO();
        dto.setId(saved.getId());
        dto.setRideId(ride.getId());
        dto.setParticipantId(participant.getId());
        dto.setParticipantName(participant.getName());
        dto.setParticipantPhoneNumber(participant.getPhoneNumber());
        dto.setPickupLocation(pickupLocation);
        dto.setPickupTime(pickupTime);

        return dto;
    }

    public Participation acceptParticipation(Long participationId) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        Ride ride = participation.getRide();
        ride.setTotalCarpoolers(ride.getTotalCarpoolers() + 1);
        rideRepository.save(ride);

        return participation;
    }

    public void rejectParticipation(Long participationId, Long rideOwnerId) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participation not found"));

        Ride ride = participation.getRide();

        if (!ride.getOwner().getId().equals(rideOwnerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ride owner can reject participation");
        }

        participationRepository.delete(participation);
    }

    public List<Participation> listByRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride not found"));

        return participationRepository.findByRide(ride);
    }
}
