package kn.kommute.app.controller;

import jakarta.validation.Valid;
import kn.kommute.app.dto.*;
import kn.kommute.app.mapper.UserMapper;
import kn.kommute.app.model.User;
import kn.kommute.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO request) {
        try {
            AuthResponseDTO response = userService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO request) {
        try {
            String message = userService.register(request.getName(), request.getEmail(), request.getPassword(), request.getConfirmPassword(), request.getPhoneNumber()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal User authenticatedUser) {
        try {
            if (authenticatedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            User user = userService.getProfile(authenticatedUser.getEmail());
            return ResponseEntity.ok(userMapper.toDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/profile/update-password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal User user, @Valid @RequestBody UpdatePasswordDTO request
    ) {
        try {
            String message = userService.updatePassword(user, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/profile/update-contact")
    public ResponseEntity<String> updateContact(@AuthenticationPrincipal User user, @Valid @RequestBody UpdateContactDTO request
    ) {
        try {
            String message = userService.updateContact(user, request.getPhoneNumber());
            return ResponseEntity.ok(message);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/profile/update-name")
    public ResponseEntity<String> updateName(@AuthenticationPrincipal User user, @Valid @RequestBody UpdateNameDTO request
    ) {
        try {
            String message = userService.updateName(user, request.getName());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
