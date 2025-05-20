package kn.kommute.app.service;

import kn.kommute.app.model.User;
import kn.kommute.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return "Login successful";
        }
        throw new RuntimeException("Invalid credentials");
    }

    public String register(String name, String email, String password, String confirmPassword, String phoneNumber) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already registered");
        }
        if(userRepository.findByphoneNumber(phoneNumber).isPresent()){
            throw new IllegalStateException("Phone Number already registered");
        }



        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(name, email, encodedPassword, phoneNumber);
        userRepository.save(newUser);
        return "User registered successfully";
    }

    public User getProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String updatePassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password updated successfully";
    }

    public String updateContact(User user, String contact) {
        user.setPhoneNumber(contact);
        if (user.getPhoneNumber().equals(contact)) {
            return "Contact unchanged (same as current)";
        }
        if(userRepository.findByphoneNumber(contact).isPresent()) {
            throw new IllegalStateException("Phone Number already registered");
        }
        userRepository.save(user);
        return "Contact updated successfully";
    }

    public String updateName(User user, String name) {
        user.setName(name);
        userRepository.save(user);
        return "Name updated successfully";
    }
}
