package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.JournalEntry;
import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.api.response.WeatherResponse;
import com.newapp.bookEntry.service.JournalEntryService;
import com.newapp.bookEntry.service.UserEntryService;
import com.newapp.bookEntry.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private WeatherService weatherService;
    public PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @GetMapping
    private List<User> getAllUsers(){
        return userEntryService.getAll();
    }
    @PutMapping
    public ResponseEntity<?> findUserByName(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userInDb=userEntryService.findByname(username);
        if(userInDb!=null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
            userEntryService.saveEntry(userInDb);
            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }
        else{

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/greet")
    public  ResponseEntity<?> greetings(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null){

            greeting=" Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+greeting,HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userEntryService.deleteUserByName(authentication.getName());
        return  new ResponseEntity<>(HttpStatus.OK);
    }

}