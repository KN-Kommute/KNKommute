package kn.kommute.app.controller;

import kn.kommute.app.dto.*;
import kn.kommute.app.mapper.UserMapper;
import kn.kommute.app.model.User;
import kn.kommute.app.security.JwtUtil;
import kn.kommute.app.security.UserPrincipal;
import kn.kommute.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já registado.");
        }
        userService.register(user);
        return ResponseEntity.ok("Utilizador registado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            User user = userService.login(request.getEmail(), request.getPassword());
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        System.out.println("[CONTROLLER] Principal: " + principal);
        if (principal == null || principal.getUser() == null) {
            System.out.println("[CONTROLLER] Principal ou user está null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = principal.getUser();
        System.out.println("[CONTROLLER] User carregado: " + user.getEmail());

        return ResponseEntity.ok(userMapper.toDTO(user));
    }




    @PutMapping("/profile/update-password")
    public ResponseEntity<String> updatePassword(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UpdatePasswordDTO dto) {
        User user = principal.getUser();
        try {
            String message = userService.updatePassword(user, dto.getOldPassword(), dto.getNewPassword());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/profile/update-contact")
    public ResponseEntity<String> updateContact(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UpdateContactDTO dto) {
        User user = principal.getUser();
        try {
            String message = userService.updateContact(user, dto.getPhoneNumber());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/profile/update-name")
    public ResponseEntity<String> updateName(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UpdateNameDTO dto) {
        User user = principal.getUser();
        try {
            String message = userService.updateName(user, dto.getName());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

