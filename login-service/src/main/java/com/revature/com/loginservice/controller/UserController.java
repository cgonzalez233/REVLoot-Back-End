package com.revature.com.loginservice.controller;

import com.revature.com.loginservice.entity.User;
import com.revature.com.loginservice.exception.UserException;
import com.revature.com.loginservice.service.UserService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Builder
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(params = { "email", "password" })
    public User getUserByCredentials(@RequestParam String email, @RequestParam String password) {
        User user = userService.getUserByCredentials(email, password);
        if(user == null){
            throw new UserException("User not found with those credentials.");
        }
        return user;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        try {
            return userService.addUser(user);
        }
        catch (DataIntegrityViolationException dive){
            throw new UserException("Registration failed. User already exists with that email.");
        }
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
        }
        catch (DataIntegrityViolationException dive){
            throw new UserException("Update failed. Another User already exists with that email.");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
