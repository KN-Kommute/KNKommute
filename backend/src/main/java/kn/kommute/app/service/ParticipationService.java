package kn.kommute.app.service;

import kn.kommute.app.dto.ParticipationDTO;
import kn.kommute.app.model.Participation;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.ParticipationRepository;
import kn.kommute.app.repository.RideRepository;
import kn.kommute.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        if (participationRepository.existsByRideIdAndUserId(rideId, userId)) {
            throw new RuntimeException("User already requested to participate in this ride.");
        }

        User participant = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Participation participation = new Participation();
        participation.setRide(ride);
        participation.setUser(participant);
        participation.setPickupLocation(pickupLocation);
        participation.setPickupTime(pickupTime);
        participation.setStatus("PENDING");

        Participation saved = participationRepository.save(participation);

        ParticipationDTO dto = new ParticipationDTO();
        dto.setId(saved.getId());
        dto.setRideId(ride.getId());
        dto.setParticipantId(participant.getId());
        dto.setParticipantName(participant.getName());
        dto.setParticipantPhoneNumber(participant.getPhoneNumber());
        dto.setPickupLocation(pickupLocation);
        dto.setPickupTime(pickupTime);
        dto.setStatus(saved.getStatus());

        return dto;
    }

    public Participation acceptParticipation(Long rideId, Long participationId, Long ownerId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("You are not the owner of this ride");
        }

        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));


        if (ride.getTotalCarpoolers() >= ride.getMaxUsers()) {
            throw new RuntimeException("Ride is already full");
        }

        participation.setStatus("ACCEPTED");
        ride.setTotalCarpoolers(ride.getTotalCarpoolers() + 1);
        rideRepository.save(ride);

        return participation;
    }

    public void rejectParticipation(Long participationId, Long rideOwnerId) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        Ride ride = participation.getRide();

        if (!ride.getOwner().getId().equals(rideOwnerId)) {
            throw new RuntimeException("Only ride owner can reject participation");
        }

        participationRepository.delete(participation);
    }


    @Transactional
    public void cancelParticipation(Long rideId, Long userId){
        participationRepository.deleteByRideIdAndUserId(rideId, userId);
    }

    public List<ParticipationDTO> listByRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        List<Participation> participations = participationRepository.findByRide(ride);
        List<ParticipationDTO> dtos = new ArrayList<>();

        for (Participation p : participations) {
            ParticipationDTO dto = new ParticipationDTO();
            dto.setId(p.getId());
            dto.setRideId(rideId);
            dto.setParticipantId(p.getUser().getId());
            dto.setParticipantName(p.getUser().getName());
            dto.setParticipantPhoneNumber(p.getUser().getPhoneNumber());
            dto.setPickupLocation(p.getPickupLocation());
            dto.setPickupTime(p.getPickupTime());
            dto.setStatus(p.getStatus());
            dtos.add(dto);
        }

        return dtos;
    }

    public List<ParticipationDTO> listByUser(Long userId) {

        List<Participation> participations = participationRepository.findByUserId(userId);
        List<ParticipationDTO> dtos = new ArrayList<>();

        for (Participation participation : participations) {
            //dtos.add(participationMapper.toDTO(participation));

            ParticipationDTO dto = new ParticipationDTO();
            dto.setId(participation.getId());
            dto.setRideId(participation.getRide().getId());
            dto.setParticipantId(participation.getUser().getId());
            dto.setParticipantName(participation.getUser().getName());
            dto.setParticipantPhoneNumber(participation.getUser().getPhoneNumber());
            dto.setPickupLocation(participation.getPickupLocation());
            dto.setPickupTime(participation.getPickupTime());
            dto.setStatus(participation.getStatus());
            dtos.add(dto);
        }

        return dtos;
    }


    public List<ParticipationDTO> listAcceptedByRide(Long rideId, Long ownerId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("You are not the owner of this ride");
        }

        List<Participation> participations = participationRepository.findByRideOrderByIdAsc(ride);
        List<ParticipationDTO> dtos = new ArrayList<>();

        int acceptedCount = ride.getTotalCarpoolers();
        for (int i = 0; i < acceptedCount && i < participations.size(); i++) {
            Participation p = participations.get(i);
            ParticipationDTO dto = new ParticipationDTO();
            dto.setId(p.getId());
            dto.setRideId(rideId);
            dto.setParticipantId(p.getUser().getId());
            dto.setParticipantName(p.getUser().getName());
            dto.setParticipantPhoneNumber(p.getUser().getPhoneNumber());
            dto.setPickupLocation(p.getPickupLocation());
            dto.setPickupTime(p.getPickupTime());
            dtos.add(dto);
        }

        return dtos;
    }
}