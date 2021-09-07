package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5001")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String login(@RequestParam("email") String email,@RequestParam("password") String password){
        return authService.login(email,password);
    }
}
