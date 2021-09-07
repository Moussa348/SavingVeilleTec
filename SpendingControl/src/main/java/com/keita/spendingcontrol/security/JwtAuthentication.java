package com.keita.spendingcontrol.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthentication implements Authentication {

    private final DecodedJWT token;

    public JwtAuthentication(DecodedJWT token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Claim claimType = token.getClaim(JwtService.PERSON_ROLE_CLAIM);

        return claimType.isNull()?null: Arrays.stream(claimType.asString().split(" ")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return "Bearer " + token.getToken();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if(!authenticated){
            throw new IllegalArgumentException("User could not be authenticated");
        }
    }

    @Override
    public String getName() {
        return token.getSubject();
    }
}
