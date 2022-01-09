package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.UserRepository;
import com.launchcode.AroundTownServer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/users/confirm/email/{email}")
    public boolean confirmEmail(@PathVariable("email") String email) {
        List<User> allUsers = getAllUsers();
        for(User user : allUsers) {
            if(email.equalsIgnoreCase(user.getEmail())) {
                System.out.println(false);
                return false;
            }
        }
        return true;
    }

    @GetMapping("/users/confirm/username/{username}")
    public boolean confirmUsername(@PathVariable("username") String username) {
        List<User> allUsers = getAllUsers();
        for(User user : allUsers) {
            if(username.equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    @GetMapping("/users/username/{username}")
    public Optional<User> getUserByUserName(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }
}
