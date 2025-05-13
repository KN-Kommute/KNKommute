package kn.kommute.app;

import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.RideRepository;
import kn.kommute.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindByOrigin() {

        User owner = new User(null, "Condutor Teste", "condutor@example.com", "password", "911111111");
        userRepository.save(owner);


        Ride ride = Ride.builder()
                .origin("Porto")
                .destination("Lisboa")
                .time(LocalDateTime.of(2025, 5, 10, 15, 30))
                .owner(owner)
                .totalValue(20.0f)
                .maxUsers(4)
                .build();

        rideRepository.save(ride);

        Optional<Ride> found = rideRepository.findByOrigin("Porto");

        assertTrue(found.isPresent());
        assertEquals("Lisboa", found.get().getDestination());
        assertEquals(4, found.get().getMaxUsers());
    }

    @Test
    public void testFindByOriginNotFound() {
        Optional<Ride> found = rideRepository.findByOrigin("Não Existe");
        assertTrue(found.isEmpty());
    }

    @Test
    public void testFindByDestination() {
        User owner = new User(null, "Condutor Destino", "destino@example.com", "password", "933333333");
        userRepository.save(owner);

        Ride ride = Ride.builder()
                .origin("Coimbra")
                .destination("Évora")
                .time(LocalDateTime.now().plusDays(3))
                .owner(owner)
                .totalValue(25.0f)
                .maxUsers(2)
                .build();

        rideRepository.save(ride);

        Optional<Ride> found = rideRepository.findByDestination("Évora");

        assertTrue(found.isPresent());
        assertEquals("Coimbra", found.get().getOrigin());
    }

    @Test
    public void testFindByTimeAfter() {
        User owner = new User(null, "Condutor Futuro", "futuro@example.com", "password", "944444444");
        userRepository.save(owner);

        LocalDateTime now = LocalDateTime.now();

        Ride futureRide1 = Ride.builder()
                .origin("Lisboa")
                .destination("Faro")
                .time(now.plusDays(1))
                .owner(owner)
                .totalValue(30.0f)
                .maxUsers(3)
                .build();

        Ride futureRide2 = Ride.builder()
                .origin("Porto")
                .destination("Braga")
                .time(now.plusDays(2))
                .owner(owner)
                .totalValue(15.0f)
                .maxUsers(4)
                .build();

        Ride pastRide = Ride.builder()
                .origin("Guimarães")
                .destination("Aveiro")
                .time(now.minusDays(1))
                .owner(owner)
                .totalValue(10.0f)
                .maxUsers(2)
                .build();

        rideRepository.saveAll(List.of(futureRide1, futureRide2, pastRide));

        List<Ride> futureRides = rideRepository.findByTimeAfter(now);

        assertEquals(2, futureRides.size());
        assertTrue(futureRides.stream().anyMatch(r -> r.getDestination().equals("Faro")));
        assertTrue(futureRides.stream().anyMatch(r -> r.getDestination().equals("Braga")));
    }
}