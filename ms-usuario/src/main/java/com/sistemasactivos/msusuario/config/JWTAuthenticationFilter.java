package com.sistemasactivos.msusuario.config;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sistemasactivos.msusuario.model.UsuarioLogin;
import com.sistemasactivos.msusuario.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsuarioLogin usuarioLogin = new UsuarioLogin();

        try {
            usuarioLogin = new ObjectMapper().readValue(request.getReader(), UsuarioLogin.class);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Error al obtener las credenciales de autenticación, error: " + e.getMessage() + ", causa: " + e.getCause());
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                usuarioLogin.getEmail(),
                usuarioLogin.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Role", " " + userDetails.getAuthorities());
        response.getWriter().flush();
        super.successfulAuthentication(request, response, filterChain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed");
    }
}