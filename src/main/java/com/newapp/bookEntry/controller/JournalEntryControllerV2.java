package com.newapp.bookEntry.controller;

import com.newapp.bookEntry.Entity.JournalEntry;
import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.service.JournalEntryService;
import com.newapp.bookEntry.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> getJournal() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        User user=userEntryService.findByname(username);
        List<JournalEntry> userList= user.getJournalEntries();
        if(userList!=null && !userList.isEmpty()){
            return new ResponseEntity<>(userList,HttpStatus.OK);
        }
        return new ResponseEntity<>("No Journals Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addJournal(@RequestBody JournalEntry newEntry) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username= authentication.getName();
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(newEntry, username);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred",HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userEntryService.findByname(username);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){

        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Journal not Found",HttpStatus.BAD_REQUEST);
        }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId id) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
        if(journalEntry.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean removed=journalEntryService.deleteJournalbyId(id,username);
        if(removed)
        return new ResponseEntity<>("Successfully Removed",HttpStatus.OK);
        else {
        return new ResponseEntity<>("Journal Not removed because journal not found",HttpStatus.NOT_FOUND    );

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userEntryService.findByname(username);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
                Optional<JournalEntry> journalEntry=journalEntryService.getById(id);
                if(journalEntry.isPresent()){
                    JournalEntry oldEntry=journalEntry.get();
                    oldEntry.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : oldEntry.getName());
                    oldEntry.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().equals("") ? newEntry.getDescription() : oldEntry.getDescription());
                    journalEntryService.saveEntry(oldEntry);
                    return new ResponseEntity<>(oldEntry,HttpStatus.OK);
                }
        }
        else {
            return new ResponseEntity<>("Journal Not found so cannot Update",HttpStatus.NOT_FOUND);
        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}