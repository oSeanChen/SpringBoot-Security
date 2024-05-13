package com.example.Spring.Security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/logoutPage")
    public String logout() {
        return "logout";
    }





}
