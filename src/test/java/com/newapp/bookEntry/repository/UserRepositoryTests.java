package com.newapp.bookEntry.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Test
    public void getUser(){
            userRepoImpl.getUserSA();
    }
}
