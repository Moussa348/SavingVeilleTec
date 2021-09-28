package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5001")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return authService.login(email, password);
    }
}
