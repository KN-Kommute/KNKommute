package kn.kommute.app;

import kn.kommute.app.model.Rides;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.RidesRepository;
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
public class RidesRepositoryTest {

    @Autowired
    private RidesRepository ridesRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindByOrigin() {

        User owner = new User(null, "Condutor Teste", "condutor@example.com", "password", "911111111");
        userRepository.save(owner);


        Rides ride = Rides.builder()
                .origin("Porto")
                .destination("Lisboa")
                .time(LocalDateTime.of(2025, 5, 10, 15, 30))
                .owner(owner)
                .totalValue(20.0f)
                .maxUsers(4)
                .build();

        ridesRepository.save(ride);

        Optional<Rides> found = ridesRepository.findByOrigin("Porto");

        assertTrue(found.isPresent());
        assertEquals("Lisboa", found.get().getDestination());
        assertEquals(4, found.get().getMaxUsers());
    }

    @Test
    public void testFindByOrigin_NotFound() {
        Optional<Rides> found = ridesRepository.findByOrigin("Não Existe");
        assertTrue(found.isEmpty());
    }

    @Test
    public void testFindMultipleRidesBySameUser() {
        User owner = new User(null, "Condutor Teste", "multi@example.com", "password", "922222222");
        userRepository.save(owner);

        Rides ride1 = Rides.builder()
                .origin("Porto")
                .destination("Braga")
                .time(LocalDateTime.now().plusDays(1))
                .owner(owner)
                .totalValue(10.0f)
                .maxUsers(3)
                .build();

        Rides ride2 = Rides.builder()
                .origin("Lisboa")
                .destination("Faro")
                .time(LocalDateTime.now().plusDays(2))
                .owner(owner)
                .totalValue(15.0f)
                .maxUsers(4)
                .build();

        ridesRepository.save(ride1);
        ridesRepository.save(ride2);

        long count = ridesRepository.findAll()
                .stream()
                .filter(r -> r.getOwner().getEmail().equals("multi@example.com"))
                .count();

        assertEquals(2, count);
    }

    @Test
    public void testFindByDestination() {
        User owner = new User(null, "Condutor Destino", "destino@example.com", "password", "933333333");
        userRepository.save(owner);

        Rides ride = Rides.builder()
                .origin("Coimbra")
                .destination("Évora")
                .time(LocalDateTime.now().plusDays(3))
                .owner(owner)
                .totalValue(25.0f)
                .maxUsers(2)
                .build();

        ridesRepository.save(ride);

        Optional<Rides> found = ridesRepository.findByDestination("Évora");

        assertTrue(found.isPresent());
        assertEquals("Coimbra", found.get().getOrigin());
    }

    @Test
    public void testFindByTimeAfter() {
        User owner = new User(null, "Condutor Futuro", "futuro@example.com", "password", "944444444");
        userRepository.save(owner);

        LocalDateTime now = LocalDateTime.now();

        Rides futureRide1 = Rides.builder()
                .origin("Lisboa")
                .destination("Faro")
                .time(now.plusDays(1))
                .owner(owner)
                .totalValue(30.0f)
                .maxUsers(3)
                .build();

        Rides futureRide2 = Rides.builder()
                .origin("Porto")
                .destination("Braga")
                .time(now.plusDays(2))
                .owner(owner)
                .totalValue(15.0f)
                .maxUsers(4)
                .build();

        Rides pastRide = Rides.builder()
                .origin("Guimarães")
                .destination("Aveiro")
                .time(now.minusDays(1))
                .owner(owner)
                .totalValue(10.0f)
                .maxUsers(2)
                .build();

        ridesRepository.saveAll(List.of(futureRide1, futureRide2, pastRide));

        List<Rides> futureRides = ridesRepository.findByTimeAfter(now);

        assertEquals(2, futureRides.size());
        assertTrue(futureRides.stream().anyMatch(r -> r.getDestination().equals("Faro")));
        assertTrue(futureRides.stream().anyMatch(r -> r.getDestination().equals("Braga")));
    }

    @Test
    public void testDatabaseIsIsolatedBetweenTests() {
        List<Rides> allRides = ridesRepository.findAll();
        assertTrue(allRides.isEmpty(), "A base de dados deveria estar vazia neste teste isolado");
    }
}