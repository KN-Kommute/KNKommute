package kn.kommute.app.repository;

import kn.kommute.app.model.Rides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RidesRepository extends JpaRepository<Rides, Long> {
    Optional<Rides> findByOrigin(String origin);
    Optional<Rides> findByDestination(String destination);
    List<Rides> findByTimeAfter(LocalDateTime dateTime);
}