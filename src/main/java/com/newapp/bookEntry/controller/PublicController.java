package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.api.response.WeatherResponse;
import com.newapp.bookEntry.config.JwtUtil;
import com.newapp.bookEntry.service.UserDetailsServiceImp;
import com.newapp.bookEntry.service.UserEntryService;
import com.newapp.bookEntry.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserEntryService userEntryService;
    @Autowired
    WeatherService weatherService;
    @PostMapping("/login")
    private ResponseEntity<?> createUser(@RequestBody User user){
//        userEntryService.setNewEntry(user);
//        return new ResponseEntity<>(user, HttpStatus.OK);
        try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails=userDetailsServiceImp.loadUserByUsername(user.getUsername()) ;

            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);

        }
    }
    @GetMapping("/greet")
    public  ResponseEntity<?> greetings(){
//        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null){

            greeting=" Weather feels like  "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+"Hi Temari"+greeting,HttpStatus.OK);
    }

    @PostMapping("/signup")
    private ResponseEntity<?> login(@RequestBody User user){
        userEntryService.setNewEntry(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
