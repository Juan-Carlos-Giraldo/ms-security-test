package com.jcg.security.Controllers;

import com.jcg.security.Models.Role;
import com.jcg.security.Models.Permission;
import com.jcg.security.Models.RolePermission;
import com.jcg.security.Repositories.PermissionRepository;
import com.jcg.security.Repositories.RolePermissionRepository;
import com.jcg.security.Repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/role-permission")
public class RolePermissionController {
    @Autowired
    private RolePermissionRepository theRolePermissionRepository;
    @Autowired
    private PermissionRepository thePermissionRepository;
    @Autowired
    private RoleRepository theRoleRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("role/{roleId}/permission/{permissionId}")
    public RolePermission create(@PathVariable String roleId,
                                 @PathVariable String permissionId){
        Role theRole=this.theRoleRepository.findById(roleId)
                                            .orElse(null);
        Permission thePermission=this.thePermissionRepository.findById((permissionId))
                                                                .orElse(null);
        if(theRole!=null && thePermission!=null){
            RolePermission newRolePermission=new RolePermission();
            newRolePermission.setRole(theRole);
            newRolePermission.setPermission(thePermission);
            return this.theRolePermissionRepository.save(newRolePermission);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        RolePermission theRolePermission = this.theRolePermissionRepository
                .findById(id)
                .orElse(null);
        if (theRolePermission != null) {
            this.theRolePermissionRepository.delete(theRolePermission);
        }
    }
    @GetMapping("role/{roleId}")
    public List<RolePermission> findPermissionsByRole(@PathVariable String roleId){
        return this.theRolePermissionRepository.getPermissionsByRole(roleId);
    }

}
