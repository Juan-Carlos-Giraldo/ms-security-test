package com.jcg.security.Controllers;

import com.jcg.security.Models.User;
import com.jcg.security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersControllers {
    @Autowired
    UserRepository theUserRepository;

    @GetMapping("")
    public List<User> find(){
        return this.theUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return this.theUserRepository.findById(id).orElse(null);
    }

    @PostMapping
    public User create(@RequestBody User newUser){
        return this.theUserRepository.save(newUser);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String id, @RequestBody User newUser){
        User actualUser = this.theUserRepository.findById(id).orElse(null);
        if (actualUser != null){
            actualUser.setName(newUser.getName());
            actualUser.setEmail(newUser.getEmail());
            actualUser.setPassword(newUser.getPassword());
            this.theUserRepository.save(actualUser);
            return actualUser;
        }else{
            return null;
        }
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable String id){
        this.theUserRepository.findById(id).ifPresent(theUser -> this.theUserRepository.delete(theUser));
    }
}