package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.api.response.WeatherResponse;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserEntryController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;


    @GetMapping("/id/{myId}")
    public ResponseEntity<?> findByUserName(@PathVariable("myId") String username) {
        User fetchedUser = userService.findUserName(username);
        if (fetchedUser != null) {
            return new ResponseEntity<>(fetchedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    ---->   Old method before using Spring security
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
        User userInDb = userService.findUserName(username);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    */


    //New Method
    //when a user is authenticated, its details are stored in SecurityContextHolder object
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findUserName(username);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userInDb.setSentimentAnalysis(user.isSentimentAnalysis());
            userInDb.setEmail(user.getEmail());
            userService.saveNewUser(userInDb);
            log.info(" User updated successfully : " +user.getUserName());
        }
        return new ResponseEntity<>("User updated successfully !",HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByName(@RequestBody User user){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // USING REST TEMPLATE CLIENT TO MAKE A GET REQUEST
    @GetMapping("/weather")
    public ResponseEntity<?> greet(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String greeting = "";
        WeatherResponse weatherResponse =  weatherService.getWeather("New Delhi");
        if(weatherResponse != null){
            greeting = " ,Weather feels like " +weatherResponse.getCurrent().getFeelsLike();
        }

        String body = "Hi "+authentication.getName() + greeting;
        return ResponseEntity.ok(body);
    }


}
