package com.newapp.bookEntry.services;

import com.newapp.bookEntry.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void newTest(){
        assertEquals(4,2+2);
        assertNotNull(userRepo.findByUsername("nithin"));
    }
}
