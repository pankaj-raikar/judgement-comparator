package com.legaltech.judgment_comparator.controller;

import com.legaltech.judgment_comparator.entity.User;
import com.legaltech.judgment_comparator.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for User Management
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * POST /api/users/register - Register new user
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        log.info("POST /api/users/register - Username: {}", user.getUsername());
        User registered = userService.registerUser(user);
        // Remove password from response for security
        registered.setPassword("***");
        return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }

    /**
     * POST /api/users/login - User login
     * Request body: {"username": "john", "password": "pass123"}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        log.info("POST /api/users/login - Username: {}", username);
        User user = userService.loginUser(username, password);

        // Return user info (without password)
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "userId", user.getId(),
                "username", user.getUsername(),
                "role", user.getRole(),
                "email", user.getEmail()));
    }

    /**
     * GET /api/users - Get all users (admin only in production)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("GET /api/users");
        List<User> users = userService.getAllUsers();
        // Remove passwords
        users.forEach(user -> user.setPassword("***"));
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/{id} - Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("GET /api/users/{}", id);
        User user = userService.getUserById(id);
        user.setPassword("***");
        return ResponseEntity.ok(user);
    }

    /**
     * DELETE /api/users/{id} - Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        log.info("DELETE /api/users/{}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
