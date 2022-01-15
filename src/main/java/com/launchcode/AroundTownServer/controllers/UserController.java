package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.data.UserRepository;
import com.launchcode.AroundTownServer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/authenticate")
    public HashMap<String, String> authenticate(@RequestBody User user) {
        Optional<User> userData = userRepository.findByUsername(user.getUsername());
        HashMap<String, String> map = new HashMap<>();
        if(userData.isPresent()) {
            User userInfo = userData.get();
            String receivedPassword =  user.getPwHash();
//            if(userInfo.isMatchingPassword(receivedPassword)) {
            if (userInfo.getPwHash().equals(receivedPassword)) {
                map.put("status", "success");
            } else {
                map.put("status", "failure");
            }
        } else {
            map.put("status", "failure");
        }
        return map;
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

    @GetMapping("/users/id/{id}")
    public Optional<User> getUserById(@PathVariable("id") int id) {
        return userRepository.findById(id);
    }

    @GetMapping("/users/username/{username}")
    public Optional<User> getUserByUserName(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

    @DeleteMapping("/userprofile/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }


}
