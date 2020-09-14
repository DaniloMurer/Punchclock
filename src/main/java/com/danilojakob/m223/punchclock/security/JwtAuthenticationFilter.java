package com.danilojakob.m223.punchclock.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser credentials = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), Collections.emptyList())
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        User applicationUser = (User) auth.getPrincipal();
        String authorities = applicationUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String token = JWT.create().withSubject(applicationUser.getUsername())
                .withClaim("roles", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                    .sign(Algorithm.HMAC256(SecurityConstants.SECRET.getBytes()));

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}
