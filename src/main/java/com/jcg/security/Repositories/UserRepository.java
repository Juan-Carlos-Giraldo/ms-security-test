package com.jcg.security.Repositories;

// Import User class
import com.jcg.security.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'email' : ?0 }")
    public User getUserByEmail(String email);
}
