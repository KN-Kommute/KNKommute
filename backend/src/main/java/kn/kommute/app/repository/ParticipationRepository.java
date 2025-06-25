package kn.kommute.app.repository;

import kn.kommute.app.model.Participation;
import kn.kommute.app.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByRide(Ride ride);
    boolean existsByRideIdAndUserId(Long rideId, Long userId);
    List<Participation> findByRideOrderByIdAsc(Ride ride);
}