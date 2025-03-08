package com.newapp.bookEntry.service;

import com.newapp.bookEntry.Entity.JournalEntry;
import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepo journalEntryRepo;

    @Autowired
    UserEntryService userEntryService;
    @Transactional
    public void saveEntry(JournalEntry newEntry, String username){
        try {

        User user=userEntryService.findByname(username);
        newEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepo.save(newEntry);
        user.getJournalEntries().add(saved);
//        user.setUsername(null);
        userEntryService.saveEntry(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void saveEntry(JournalEntry newEntry){
        newEntry.setDate(LocalDateTime.now());
        journalEntryRepo.save(newEntry);

    }
    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepo.findById(id);
    }
    @Transactional
    public boolean deleteJournalbyId(ObjectId id, String username){
        boolean removed=false;
        try {


            User user = userEntryService.findByname(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {

                userEntryService.saveEntry(user);
                journalEntryRepo.deleteById(id);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting", e);
        }
        return removed;
    }

}
