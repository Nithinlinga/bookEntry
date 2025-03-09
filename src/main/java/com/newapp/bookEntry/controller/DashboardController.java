package com.newapp.bookEntry.controller;


import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.repository.UserRepoImpl;
import com.newapp.bookEntry.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    private ResponseEntity<?> getMydetails(User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userDetails=userEntryService.findByname(username);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);

    }
}
