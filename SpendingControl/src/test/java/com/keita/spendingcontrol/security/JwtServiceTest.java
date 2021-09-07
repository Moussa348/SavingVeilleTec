package com.keita.spendingcontrol.security;

import com.keita.spendingcontrol.model.entity.Person;
import jdk.jfr.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {

    JwtService jwtService;

    @BeforeEach
    void init() {
        jwtService = new JwtService();
    }

    @Test
    void generate(){
        //ARRANGE
        Person person = Person.builder().id(1L).roles("USER").build();
        Long dailyExpenseId = 1L;

        //ACT
        String token = jwtService.generate(person,dailyExpenseId);

        //ASSERT
        assertEquals(person.getId(), jwtService.getJwtVerifier().verify(token).getClaim(JwtService.PERSON_ID_CLAIM).asLong());
    }
}
