package com.revature.com.loginservice.service;

import com.revature.com.loginservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(Long id);
    public User getUserByCredentials(String email, String password);
    public User getUserByEmail(String email);
    public User addUser(User user);
    public void updateUser(Long id, User user);
    public void deleteUser(Long id);
}
