package com.launchcode.AroundTownServer.controllers;

import antlr.StringUtils;
import com.launchcode.AroundTownServer.data.UserRepository;
import com.launchcode.AroundTownServer.models.User;

public class UserValidator {

    private UserRepository userRepository;

    public boolean emailExists(String email) {
        boolean exists = false;

        if (email != null && !email.isBlank()) {
            User user = userRepository.findByEmail(email);
            exists = (user != null);
        }

        return exists;
    }
}
