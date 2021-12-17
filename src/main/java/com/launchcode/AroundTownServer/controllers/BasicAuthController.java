package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.AuthenticationBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class BasicAuthController {

    @GetMapping(path = "/login")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }
}
