package kn.kommute.app.repository;

import kn.kommute.app.model.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteRepository extends JpaRepository<Commute, Long> {
}


