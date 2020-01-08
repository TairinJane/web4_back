package pip.pip4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pip.pip4.dtos.UserDTO;
import pip.pip4.entities.Role;
import pip.pip4.entities.User;
import pip.pip4.repositories.UserRepository;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
@EnableAutoConfiguration
public class RegistrationController {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String greeting() {
        return "Hello, bro :(";
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@Validated @RequestBody UserDTO userDTO) {
        System.out.println("Registration request: " + userDTO.getUsername());

        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            return ResponseEntity.badRequest().body("This username is already in use");
        }

        User user = new User(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return ResponseEntity.ok("Registration completed");
    }

    @GetMapping("/username")
    ResponseEntity<String> getUsername(Authentication authentication) {
        try {
            String username = authentication.getName();
            System.out.println("request /username by " + username);
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}