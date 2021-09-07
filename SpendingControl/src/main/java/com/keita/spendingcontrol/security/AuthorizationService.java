package com.keita.spendingcontrol.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isConnected(Long id) {
        return ((DecodedJWT) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .getClaim(JwtService.PERSON_ID_CLAIM).asLong().equals(id);
    }
}
