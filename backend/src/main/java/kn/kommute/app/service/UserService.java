package kn.kommute.app.service;

import kn.kommute.app.dto.AuthResponseDTO;
import kn.kommute.app.dto.UserDTO;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        String token = jwtService.generateToken(user);

        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), user.getPhoneNumber());

        return new AuthResponseDTO(userDTO, token);
    }

    // Regista novo user, com validações
    public String register(String name, String email, String password, String confirmPassword, String phoneNumber) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already registered");
        }

        if (userRepository.findByphoneNumber(phoneNumber).isPresent()) {
            throw new IllegalStateException("Phone number already registered");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(name, email, encodedPassword, phoneNumber);
        userRepository.save(newUser);
        return "User registered successfully";
    }

    // Retorna user pelo email (profile)
    public User getProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Atualiza password, valida a antiga
    public String updatePassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password updated successfully";
    }

    // Atualiza phoneNumber, valida duplicados e alteração
    public String updateContact(User user, String newPhoneNumber) {
        if (user.getPhoneNumber().equals(newPhoneNumber)) {
            return "Contact unchanged (same as current)";
        }

        if (userRepository.findByphoneNumber(newPhoneNumber).isPresent()) {
            throw new IllegalStateException("Phone number already registered");
        }

        user.setPhoneNumber(newPhoneNumber);
        userRepository.save(user);
        return "Contact updated successfully";
    }

    // Atualiza nome, valida alteração
    public String updateName(User user, String newName) {
        if (user.getName().equals(newName)) {
            return "Name unchanged (same as current)";
        }

        user.setName(newName);
        userRepository.save(user);
        return "Name updated successfully";
    }
}
