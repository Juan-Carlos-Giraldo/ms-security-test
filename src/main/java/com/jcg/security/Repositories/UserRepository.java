package com.jcg.security.Repositories;

// Import User class
import com.jcg.security.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
