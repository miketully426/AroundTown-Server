package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.UserRepository;
import com.launchcode.AroundTownServer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    //Get Mapping users/confirm/{{username}}
    //takes @PathVariable of username
    //loops through all user usernames if it matches one, send false, if not send true
    @GetMapping("users/confirm/{email}")
    public boolean confirmEmail(@PathVariable("email") String email) {
        List<User> allUsers = getAllUsers();

        for(User user : allUsers) {
            if(email.equalsIgnoreCase(user.getEmail())) {
                return false;
            }
        }
        return true;
    }
}
