package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    public String login(String email, String password) {
        return jwtService.generate(personService.findPersonByEmailAndPassword(email, password, HttpStatus.UNAUTHORIZED));
    }

    public void resetPassword(String email) throws MessagingException {
       emailService.resetPassword(personService.findPersonByEmail(email));
    }
}
