package kn.kommute.app.service;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.RideRepository;
import kn.kommute.app.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.*;

@Service
@Transactional
public class RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    @Autowired
    public RideService(RideRepository rideRepository, RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.rideMapper = rideMapper;
    }

    public Ride createRide(User user, RideDTO dto) {
        Ride ride = rideMapper.toRide(dto, user);
        return rideRepository.save(ride);
    }

    public List<RideDTO> listRides() {
        List<Ride> rides = rideRepository.findAll();
        List<RideDTO> response = new ArrayList<>();
        for (Ride ride : rides) {
            response.add(rideMapper.toRideDTO(ride));
        }
        return response;
    }

    public RideDTO participate(User user, Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride not found"));

        if (ride.getTotalCarpoolers() >= ride.getMaxUsers()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ride is full");
        }

        ride.setTotalCarpoolers(ride.getTotalCarpoolers() + 1);
        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toRideDTO(savedRide);
    }

    public Optional<RideDTO> findRideById(Long id) {
        return rideRepository.findById(id)
                .map(ride -> rideMapper.toRideDTO(ride));
    }

    public Optional<RideDTO> findRideByOrigin(String origin) {
        return rideRepository.findByOrigin(origin)
                .map(ride -> rideMapper.toRideDTO(ride));
    }

    public RideDTO updateRide(Long id, Ride updatedRide, User userRequest) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ride not found"));

        if (!ride.getOwner().getId().equals(userRequest.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this ride");
        }

        ride.setOrigin(updatedRide.getOrigin());
        ride.setDestination(updatedRide.getDestination());
        ride.setTime(updatedRide.getTime());
        ride.setTotalValue(updatedRide.getTotalValue());
        ride.setMaxUsers(updatedRide.getMaxUsers());

        Ride savedRide = rideRepository.save(ride);
        return rideMapper.toRideDTO(savedRide);
    }

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }

    public void deleteById(Long id) {

        Long currentUserId = getAuthenticatedUserId();

        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getOwner().getId().equals(currentUserId)) {
            throw new RuntimeException("You do not have permission to delete this ride");
        }
        rideRepository.deleteById(id);

    }
}
