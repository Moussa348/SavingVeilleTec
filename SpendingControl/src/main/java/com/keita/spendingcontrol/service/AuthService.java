package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.security.JwtService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

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

    @Transactional
    public void resetPassword(String email) throws MessagingException {
        Person person = personService.findPersonByEmail(email);

        person.setVerificationCode(RandomString.make(16));

        emailService.resetPassword(person);
    }
}
