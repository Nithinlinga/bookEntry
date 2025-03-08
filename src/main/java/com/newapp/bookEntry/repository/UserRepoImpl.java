package com.newapp.bookEntry.repository;

import com.newapp.bookEntry.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserSA(String username){
        Query query=new Query();
        query.addCriteria(Criteria.where("username").is(username));
        List<User> users=mongoTemplate.find(query,User.class);

        return users;

    }
}
