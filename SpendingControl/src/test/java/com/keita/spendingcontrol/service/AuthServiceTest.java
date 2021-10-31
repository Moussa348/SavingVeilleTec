package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import com.keita.spendingcontrol.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    JwtService jwtService;

    @Mock
    PersonService personService;

    @InjectMocks
    AuthService authService;

    @Test
    void login(){
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").password("araa").build();
        when(jwtService.generate(person1)).thenReturn("dsadasdasdas");
        when(personService.findPersonByEmailAndPassword(person1.getEmail(),person1.getPassword(), HttpStatus.UNAUTHORIZED)).thenReturn(person1);

        Person person2 = Person.builder().id(2L).email("bbbbb@gmail.com").password("bbbb").build();
        when(personService.findPersonByEmailAndPassword(person2.getEmail(),person2.getPassword(),HttpStatus.UNAUTHORIZED)).thenThrow(ResponseStatusException.class);

        //ACT
        String token = authService.login(person1.getEmail(),person1.getPassword());

        //ASSERT
        assertNotNull(token);
        assertThrows(ResponseStatusException.class,()->authService.login(person2.getEmail(),person2.getPassword()));
    }

    @Test
    void resetPassword(){
        //ARRANGE
        Person person1 = Person.builder().id(1L).email("araa@gmail.com").password("araa").build();
        when(jwtService.generate(person1)).thenReturn("dsadasdasdas");
        when(personService.findPersonByEmailAndPassword(person1.getEmail(),person1.getPassword(), HttpStatus.UNAUTHORIZED)).thenReturn(person1);

        Person person2 = Person.builder().id(2L).email("bbbbb@gmail.com").password("bbbb").build();
        when(personService.findPersonByEmailAndPassword(person2.getEmail(),person2.getPassword(),HttpStatus.UNAUTHORIZED)).thenThrow(ResponseStatusException.class);

        //ACT
        String token = authService.login(person1.getEmail(),person1.getPassword());

        //ASSERT
        assertNotNull(token);
        assertThrows(ResponseStatusException.class,()->authService.login(person2.getEmail(),person2.getPassword()));
    }
}
