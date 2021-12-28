package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.UserRepository;
import com.launchcode.AroundTownServer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@ResponseBody
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users")
    void addUser(@RequestBody User user) {
        User newUser = new User(user.getName(), user.getUsername(), user.getEmail(), user.getPwHash());
        userRepository.save(newUser);
    }

    @PostMapping("/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody Map<String, Object> inputData) {
        String email = (String)inputData.get("email");
        LOG.debug("Checking for existing email " + email);

        Boolean bool = userValidator.emailExists(email);

        return ResponseEntity.status(HttpStatus.OK).body(bool);
    }
}
