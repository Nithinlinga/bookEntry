package com.newapp.bookEntry.service;

import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user=userRepo.findByUsername(username);
        if(user!=null){
            UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0])).build();
            return userDetails;
        }
        throw new UsernameNotFoundException("Username not found"+username);
    }
}
