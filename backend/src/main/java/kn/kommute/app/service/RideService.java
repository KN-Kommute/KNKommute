package kn.kommute.app.service;

import kn.kommute.app.model.Ride;
import kn.kommute.app.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RideService {

    private final RideRepository ridesRepository;

    @Autowired
    public RideService(RideRepository ridesRepository) {
        this.ridesRepository = ridesRepository;
    }

    public List<Ride> findAllRides() {
        return ridesRepository.findAll();
    }

    public Optional<Ride> findRideById(Long id) {
        return ridesRepository.findById(id);
    }

    public Optional<Ride> findRideByOrigin(String origin) {
        return ridesRepository.findByOrigin(origin);
    }

    public Ride saveRide(Ride ride) {
        return ridesRepository.save(ride);
    }

    public Ride updateRide(Long id, Ride updatedRide) {
        Optional<Ride> existingRide = ridesRepository.findById(id);
        if (existingRide.isPresent()) {
            Ride ride = existingRide.get();
            ride.setOrigin(updatedRide.getOrigin());
            ride.setDestination(updatedRide.getDestination());
            ride.setTime(updatedRide.getTime());
            ride.setTotalValue(updatedRide.getTotalValue());
            ride.setMaxUsers(updatedRide.getMaxUsers());
            return ridesRepository.save(ride);
        }
        return null;
    }

    public void deleteRide(Long id) {
        ridesRepository.deleteById(id);
    }
}
