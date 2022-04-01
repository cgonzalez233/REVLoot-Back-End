package com.revature.com.loginservice.service;

import com.revature.com.loginservice.entity.User;
import com.revature.com.loginservice.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Builder
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        User user = repository.findByEmail(email);
        if(user==null)
            return null;
        if(passwordEncoder.matches(password, user.getPassword()))
            return user;
        return null;
    }

    @Override
    public User getUserByEmail(String email){
        return repository.findByEmail(email);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        User userDb = repository.findById(id).orElse(null);
        if(userDb == null)
            return;
        userDb.setEmail(user.getEmail());
        if(user.getPassword() != null)
            userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        repository.save(userDb);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
