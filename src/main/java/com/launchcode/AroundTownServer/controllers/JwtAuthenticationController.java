package com.launchcode.AroundTownServer.controllers;

import com.launchcode.AroundTownServer.config.JwtTokenUtil;
import com.launchcode.AroundTownServer.models.JwtRequest;
import com.launchcode.AroundTownServer.models.JwtResponse;
import com.launchcode.AroundTownServer.models.User;
import com.launchcode.AroundTownServer.models.UserDTO;
import com.launchcode.AroundTownServer.service.JwtUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        System.out.println("----->" + authenticationRequest.getUsername() + authenticationRequest.getPwHash());

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPwHash());

        System.out.println("-----> i am here");


        User user = new User("", authenticationRequest.getUsername(), "", authenticationRequest.getPwHash());
//       final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(authenticationRequest.getUsername(), authenticationRequest.getPwHash());
//
       // final String token = jwtTokenUtil.generateToken(userDetails);

        String token = doGenerateToken(new HashMap<>(), user.getUsername());

        System.out.println("----->" + token);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

