package kn.kommute.app.service;

import kn.kommute.app.model.User;
import kn.kommute.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email ou palavra-passe inválidos"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Email ou palavra-passe inválidos");
        }

        return user;
    }

    public String updatePassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Palavra-passe atual incorreta.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Palavra-passe atualizada com sucesso.";
    }

    public String updateContact(User user, String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return "Contacto atualizado com sucesso.";
    }

    public String updateName(User user, String newName) {
        user.setName(newName);
        userRepository.save(user);
        return "Nome atualizado com sucesso.";
    }
}
