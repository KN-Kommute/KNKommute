package kn.kommute.app.repository;

import kn.kommute.app.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Optional<Ride> findByOrigin(String origin);

    Optional<Ride> findByDestination(String destination);

    List<Ride> findByTimeAfter(LocalDateTime dateTime);
}