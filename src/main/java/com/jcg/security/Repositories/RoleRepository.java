package com.jcg.security.Repositories;

import com.jcg.security.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
