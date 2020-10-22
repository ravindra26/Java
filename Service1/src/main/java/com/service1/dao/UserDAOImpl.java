package com.service1.dao;

import com.service1.mongo.documents.User;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * Users Dao Impl
 * 
 * @author Ravindra
 * @since 2020-10-16
 */
public class UserDAOImpl {
    
    @Autowired
    private MongoOperations mongoOps;
    @Autowired
    private MongoTemplate template;
    private static final String USER_COLLECTION = "user";
    
    Logger logger = Logger.getLogger("myLogger");

    /**
     * Retrieves all Employees
     *
     * @return
     */
    public List<User> listAllEmployeess() {

        List<User> users = mongoOps.findAll(User.class);
        
        return users;
    }
    
    public User getUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        query.limit(1);
        
        User user = mongoOps.findOne(query, User.class, USER_COLLECTION);
        return user;
    }
}
