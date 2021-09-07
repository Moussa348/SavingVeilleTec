package com.keita.spendingcontrol.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keita.spendingcontrol.model.entity.Person;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@Service
public class JwtService {

    private final long duration;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    public static String PERSON_ROLE_CLAIM = "role";
    public static String PERSON_ID_CLAIM = "personId";

    public JwtService() {
        this.algorithm = Algorithm.HMAC256(SecureRandom.getSeed(16));
        this.jwtVerifier = JWT.require(algorithm).build();
        this.duration = TimeUnit.HOURS.toMillis(2);
    }

    public String generate(Person person) {
        final long time = System.currentTimeMillis();

        return JWT.create()
                .withSubject(person.getId().toString())
                .withClaim(PERSON_ID_CLAIM, person.getId())
                .withClaim(PERSON_ROLE_CLAIM,person.getRoles())
                .withIssuedAt(new Date(time))
                .withExpiresAt(new Date(time + duration))
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) throws JWTVerificationException, JWTDecodeException {
        if (token == null)
            throw new JWTVerificationException("Token cannot be null,");

        if (token.startsWith("Bearer "))
            token = token.replace("Bearer ", "");

        else
            throw new JWTDecodeException("Can't decode");

        return jwtVerifier.verify(token);
    }
}
