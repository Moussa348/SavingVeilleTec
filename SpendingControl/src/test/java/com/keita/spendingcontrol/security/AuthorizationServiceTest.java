package com.keita.spendingcontrol.security;

import com.keita.spendingcontrol.model.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthorizationServiceTest {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthorizationService authorizationService;

    @Test
    void isConnected() {
        //ARRANGE
        Person person = Person.builder().id(1L).roles("USER").build();
        Long dailyExpenseId = 1L;
        String token = "Bearer " + jwtService.generate(person, dailyExpenseId);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(jwtService.verify(token)));

        //ACT
        boolean isConnected = authorizationService.isConnected(person.getId());

        //ASSERT
        assertTrue(isConnected);
    }
}
