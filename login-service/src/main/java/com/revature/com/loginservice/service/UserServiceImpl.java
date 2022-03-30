package com.revature.com.loginservice.service;

import com.revature.com.loginservice.entity.User;
import com.revature.com.loginservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    public User addUser(User user) {
        return repository.save(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        User userDb = repository.findById(id).get();
        userDb.setEmail(user.getEmail());
        userDb.setPassword(user.getPassword());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        repository.save(userDb);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
