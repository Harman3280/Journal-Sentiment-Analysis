package org.example.controller;

import org.example.entity.User;
import org.example.service.UserDetailsServiceImpl;
import org.example.service.UserService;
import org.example.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("health")
    public String healthCheck(){
        return "Service is Up and running !";
    }

//    @PostMapping("/create-user")
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {
            boolean userCreated = userService.saveNewUser(user);
            if(userCreated) {
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            // 1. First we will check username and password are valid using authenticationManager (Bean created in Spring Security config)
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            // 2. Second we will fetch user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
