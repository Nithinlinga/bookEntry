package com.newapp.bookEntry.service;

import com.newapp.bookEntry.Entity.JournalEntry;
import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.repository.JournalEntryRepo;
import com.newapp.bookEntry.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    UserRepo userRepo;
    public PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public void saveEntry(User user){
        userRepo.save(user);
    }
    public void setNewEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
    }
    public void saveAdmin(User admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(admin);

    }
    public List<User> getAll(){
        return userRepo.findAll();
    }
    public Optional<User> getById(ObjectId id){
        return userRepo.findById(id);
    }
    public void deleteJournalbyId(ObjectId id){
        userRepo.deleteById(id);
    }
    public void deleteUserByName(String username){
        userRepo.deleteByUsername(username);
    }
    public User findByname(String username){
        return userRepo.findByUsername(username);
    }

}
