package com.keita.spendingcontrol.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            DecodedJWT token = jwtService.verify(httpServletRequest.getHeader("Authorization"));

            if(token != null)
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(token));

        }catch (Exception e){
            log.warning(e.getMessage().toUpperCase());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
