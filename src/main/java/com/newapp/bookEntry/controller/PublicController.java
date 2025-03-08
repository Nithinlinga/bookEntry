package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserEntryService userEntryService;
    @PostMapping("/create-user")
    private void createUser(@RequestBody User user){
        userEntryService.setNewEntry(user);
    }
}
