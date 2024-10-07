package com.jcg.security.Controllers;

import com.jcg.security.Models.Permission;
import com.jcg.security.Repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/public/permissions")
public class PermissionsController {
    @Autowired
    private PermissionRepository thePermissionRepository;

    @GetMapping("")
    public List<Permission> findAll(){
        return this.thePermissionRepository.findAll();
    }

    @GetMapping("{url}/{method}")
    public Permission findPermission(@PathVariable String url, @PathVariable String method){
        return this.thePermissionRepository.getPermission(url, method);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permission create(@RequestBody Permission theNewPermission){
        return this.thePermissionRepository.save(theNewPermission);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/all")
    public List<Permission> createAll(@RequestBody List<Permission> theNewPermissions){
        return this.thePermissionRepository.saveAll(theNewPermissions);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public Permission update(@PathVariable String id, @RequestBody Permission thePermission){
        Permission thePermissionToUpdate = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        if (thePermissionToUpdate != null) {
            thePermissionToUpdate.setUrl(thePermission.getUrl());
            thePermissionToUpdate.setMethod(thePermission.getMethod());
            return this.thePermissionRepository.save(thePermissionToUpdate);
        } else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Permission thePermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        if (thePermission != null) {
            this.thePermissionRepository.delete(thePermission);
        }
    }
}
