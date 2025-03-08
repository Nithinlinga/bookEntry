package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.EmailDetails;
import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.cache.AppCache;
import com.newapp.bookEntry.repository.UserRepoImpl;
import com.newapp.bookEntry.repository.schedulers.UserScheduler;
import com.newapp.bookEntry.service.EmailService;
import com.newapp.bookEntry.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserRepoImpl userRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AppCache appCache;
    @Autowired
    private UserScheduler userScheduler;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all=userEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdmin(@RequestBody User admin){
        userEntryService.saveAdmin(admin);
        return new ResponseEntity<>(admin,HttpStatus.CREATED);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();

    }
    @GetMapping("/send")
    public void sendEmail(@RequestBody EmailDetails emailDetails){


        emailService.mailSender(emailDetails.getEmail(),emailDetails.getSubject(),emailDetails.getBody());

    }@GetMapping("/sentiment")
    public String sendEmailWithSa(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        userScheduler.getEmail(username);
        return userScheduler.fetchUsersAndSaMail();

    }

}
