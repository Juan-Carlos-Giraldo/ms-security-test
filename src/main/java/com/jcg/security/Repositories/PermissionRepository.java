package com.jcg.security.Repositories;

import com.jcg.security.Models.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String> {
}
