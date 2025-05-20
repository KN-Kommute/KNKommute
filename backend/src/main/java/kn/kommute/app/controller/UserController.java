package kn.kommute.app.controller;

import kn.kommute.app.dto.UserDTO;
import kn.kommute.app.mapper.UserMapper;
import kn.kommute.app.model.User;
import kn.kommute.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            String message = userService.login(email, password);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String phoneNumber) {
        try {
            String message = userService.register(name, email, password, confirmPassword, phoneNumber);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@RequestParam String email) {
        try {
            User user = userService.getProfile(email);
            return ResponseEntity.ok(userMapper.toDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/profile/update-password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal User user, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            String message = userService.updatePassword(user, oldPassword, newPassword);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/profile/update-contact")
    public ResponseEntity<String> updateContact(@AuthenticationPrincipal User user, @RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.updateContact(user, phoneNumber));
    }

    @PutMapping("/profile/update-name")
    public ResponseEntity<String> updateName(@AuthenticationPrincipal User user, @RequestParam String name) {
        return ResponseEntity.ok(userService.updateName(user, name));
    }
}
