package kn.kommute.app.service;

import kn.kommute.app.model.Rides;
import kn.kommute.app.repository.RidesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RidesService {

    private final RidesRepository ridesRepository;

    @Autowired
    public RidesService(RidesRepository ridesRepository) {
        this.ridesRepository = ridesRepository;
    }

    public List<Rides> findAllRides() {
        return ridesRepository.findAll();
    }

    public Optional<Rides> findRideById(Long id) {
        return ridesRepository.findById(id);
    }

    public Optional<Rides> findRideByOrigin(String origin) {
        return ridesRepository.findByOrigin(origin);
    }

    public Rides saveRide(Rides ride) {
        return ridesRepository.save(ride);
    }

    public Rides updateRide(Long id, Rides updatedRide) {
        Optional<Rides> existingRide = ridesRepository.findById(id);
        if (existingRide.isPresent()) {
            Rides ride = existingRide.get();
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
