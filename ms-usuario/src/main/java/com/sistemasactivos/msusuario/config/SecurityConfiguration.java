package com.sistemasactivos.msusuario.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    /**
     * Configura y devuelve el AuthenticationManager.
     *
     * @param http La configuración HttpSecurity.
     * @return El AuthenticationManager configurado.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    /**
     * Configura y devuelve el SecurityFilterChain.
     *
     * @param http         La configuración HttpSecurity.
     * @param authManager  El AuthenticationManager.
     * @return El SecurityFilterChain configurado.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return http
                .csrf().disable() // Deshabilitar la protección CSRF
                .authorizeRequests()
                    .requestMatchers("/signup").permitAll() // Permitir el acceso a la ruta /signup sin autenticación
                    .requestMatchers("/login").permitAll() // Permitir el acceso a la ruta /login sin autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                .and()
                .sessionManagement() // Configuración de gestión de sesión
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Establecer la política de creación de sesiones en STATELESS
                .and()
                .addFilter(jwtAuthenticationFilter) // Agregar el filtro de autenticación JWT
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // Agregar el filtro de autorización JWT antes del filtro UsernamePasswordAuthenticationFilter
                .build();
    }

    /**
     * Crea un codificador de contraseñas.
     *
     * @return El PasswordEncoder creado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}