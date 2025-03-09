package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.config.JwtUtil;
import com.newapp.bookEntry.service.UserDetailsServiceImp;
import com.newapp.bookEntry.service.UserEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @PostMapping("/signup")
    private ResponseEntity<?> login(@RequestBody User user){
        userEntryService.setNewEntry(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
