package com.keita.spendingcontrol.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keita.spendingcontrol.model.entity.Person;
import jdk.jfr.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.keita.spendingcontrol.security.JwtService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(person.getId(), jwtService.getJwtVerifier().verify(token).getClaim(PERSON_ID_CLAIM).asLong());
    }

    @Test
    void verify(){
        //ARRANGE
        Person person = Person.builder().id(1L).roles("USER").build();
        Long dailyExpenseId = 1L;
        String token1 = "Bearer " + JWT.create().withSubject(person.getId().toString())
                .withClaim(PERSON_ID_CLAIM, person.getId())
                .withClaim(PERSON_ROLE_CLAIM,person.getRoles())
                .withClaim(PERSON_DAILY_EXPENSE_ID_CLAIM, dailyExpenseId.toString())
                .sign(jwtService.getAlgorithm());
        String token2 =  "asdadsasdasdads";

        //ACT
        DecodedJWT decodedJWT = jwtService.verify(token1);

        //ASSERT
        assertEquals(person.getId(),decodedJWT.getClaim(PERSON_ID_CLAIM).asLong());
        assertThrows(JWTDecodeException.class,() -> jwtService.verify(token2));
        assertThrows(JWTVerificationException.class,()->jwtService.verify(token2));
    }
}
