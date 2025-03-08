package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserEntryService userEntryService;
    @PostMapping("/create-user")
    private ResponseEntity<?> createUser(@RequestBody User user){userEntryService.setNewEntry(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
