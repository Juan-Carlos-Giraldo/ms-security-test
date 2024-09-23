package com.jcg.security.Controllers;

import com.jcg.security.Models.Permission;
import com.jcg.security.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permissions")
public class PermissionsControllers {
    @Autowired
    PermissionRepository thePermissionRepository;

    @GetMapping("")
    public List<Permission> find(){
        return this.thePermissionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permission findById(@PathVariable String id) {
        return this.thePermissionRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Permission create(@RequestBody Permission newPermission){
        return this.thePermissionRepository.save(newPermission);
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable String id, @RequestBody Permission newPermission){
        Permission actualPermission = this.thePermissionRepository.findById(id).orElse(null);
        if (actualPermission != null){
            actualPermission.setUrl(newPermission.getUrl());
            actualPermission.setMethod(newPermission.getMethod());
            this.thePermissionRepository.save(actualPermission);
            return actualPermission;
        }else{
            return null;
        }
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable String id){
        this.thePermissionRepository.findById(id).ifPresent(thePermission -> this.thePermissionRepository.delete(thePermission));
    }
}
