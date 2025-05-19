package com.example.BankApplication.controller;

import com.example.BankApplication.entity.User;
import com.example.BankApplication.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    @PermitAll
    public ResponseEntity<?> createUser(@RequestParam String fullName, @RequestParam String email, @RequestParam String password) {
        if (userService.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Пользователь с таким email уже существует");
        }
        return ResponseEntity.ok(userService.createUser(fullName, email, password));
    }

    @PostMapping("/createAdmin")
    @PermitAll
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAdmin(@RequestParam String fullName, @RequestParam String email, @RequestParam String password) {
        if (userService.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Пользователь с таким email уже существует");
        }
        return ResponseEntity.ok(userService.createAdmin(fullName, email, password));
    }

    @PutMapping("/blockUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> blockUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.blockUser(id));
    }

    @PutMapping("/unblockUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> unblockUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.unblockUser(id));
    }

    @PutMapping("/GetUserByEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PutMapping("/GetUserById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getListOfUsers() {
        return ResponseEntity.ok(userService.getListOfUsers());
    }

    @PutMapping("/refactorUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> refactorUser(@PathVariable Long id, @RequestParam String fullName, @RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userService.refactorUser(id, fullName, email, password));
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Пользователь успешно удален");
    }

    @GetMapping("/getProfile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findByEmail(email);
        System.out.println(user);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return null;
    }
}
