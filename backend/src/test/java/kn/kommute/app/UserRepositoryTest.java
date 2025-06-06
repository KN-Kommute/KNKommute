package kn.kommute.app;

import kn.kommute.app.model.User;
import kn.kommute.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindByEmail() {
        User user = new User();
        user.setName("User Test");
        user.setEmail("teste@example.com");
        user.setPassword("12345678");
        user.setPhoneNumber("910000000");

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("teste@example.com");

        assertTrue(found.isPresent());

        User foundUser = found.get();
        assertNotNull(foundUser.getId());
        assertEquals("User Test", foundUser.getName());
        assertEquals("teste@example.com", foundUser.getEmail());
        assertEquals("12345678", foundUser.getPassword());
        assertEquals("910000000", foundUser.getPhoneNumber());
    }



    @Test
    public void testFindByEmailNotFound() {
        Optional<User> found = userRepository.findByEmail("naoexiste@example.com");
        assertTrue(found.isEmpty());
    }

    @Test
    public void testDuplicateEmailShouldFail() {
        User user1 = new User(null, "User1", "dup@example.com", "pass", "919191919");
        User user2 = new User(null, "User2", "dup@example.com", "pass", "929292929");

        userRepository.save(user1);

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.saveAndFlush(user2),
                "Expected a DataIntegrityViolationException due to duplicate email");
    }

    @Test
    public void testDuplicatePhoneNumberShouldFail() {
        User user1 = new User(null, "User1", "user1@example.com", "pass", "912345678");
        User user2 = new User(null, "User2", "user2@example.com", "pass", "912345678");

        userRepository.save(user1);

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.saveAndFlush(user2),
                "Expected a DataIntegrityViolationException due to duplicate phone number");
    }

}
