package com.keita.spendingcontrol.controller;

import com.keita.spendingcontrol.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

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

    @GetMapping("/resetPassword/{email}")
    public void resetPassword(@PathVariable String email) throws MessagingException {
        authService.resetPassword(email);
    }
}
