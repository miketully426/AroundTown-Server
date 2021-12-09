package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.UserRepository;
import com.launchcode.AroundTownServer.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userRepository.findById(id).get();
    }

    @PostMapping("/user")
    public HttpStatus addUser(@RequestBody User user) {
        userRepository.save(user);
        return HttpStatus.CREATED;
    }

}
