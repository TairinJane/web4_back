package pip.pip4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pip.pip4.authentication.JwtUtil;
import pip.pip4.dtos.UserDTO;
import pip.pip4.entities.User;
import pip.pip4.services.JwtService;
import pip.pip4.services.UserService;

@Controller
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String greeting(@Validated @RequestBody UserDTO userDTO) {
        return "Hello, " + userDTO.getUsername() + " with password " + userDTO.getPassword();
    }

    @PostMapping("/registration")
    ResponseEntity<String> registration(@Validated @RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        try {
            User user = userService.addUser(username, password);
            String token = jwtService.login(username, password);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@Validated @RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String token = jwtService.login(username, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHeader) {
        String token = jwtUtil.getTokenFromHeader(tokenHeader);
        String login = jwtUtil.getUsernameFromToken(token);
        jwtService.logout(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
