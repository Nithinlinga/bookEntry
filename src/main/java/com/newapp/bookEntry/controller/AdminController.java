package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserEntryService userEntryService;

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

}
