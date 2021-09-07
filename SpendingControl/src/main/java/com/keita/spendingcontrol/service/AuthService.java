package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private PersonService personService;


    public String login(String email,String password){
        return jwtService.generate(personService.findPersonByEmailAndPassword(email,password));
    }


}
