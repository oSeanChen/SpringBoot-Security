package com.example.Spring.Security.controller;

import com.example.Spring.Security.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/welcome")
    public Authentication welcome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return authentication;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
