package com.example.Spring.Security.service;

import com.example.Spring.Security.model.User;
import com.example.Spring.Security.dao.UserRepo;
import com.example.Spring.Security.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(int id) {
        return repo.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return repo.save(user);
    }
}
