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

    /**
     * Intenta autenticar al usuario utilizando las credenciales proporcionadas en la solicitud.
     *
     * @param request  La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @return La instancia de Authentication que representa la autenticación exitosa.
     * @throws AuthenticationException Si la autenticación falla.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsuarioLogin usuarioLogin = new UsuarioLogin();

        try {
            // Leer y mapear el cuerpo de la solicitud a la clase UsuarioLogin
            usuarioLogin = new ObjectMapper().readValue(request.getReader(), UsuarioLogin.class);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Error al obtener las credenciales de autenticación, error: " + e.getMessage() + ", causa: " + e.getCause());
        }

        // Crear una instancia de UsernamePasswordAuthenticationToken con las credenciales del usuario
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                usuarioLogin.getEmail(),
                usuarioLogin.getPassword(),
                Collections.emptyList()
        );

        // Realizar la autenticación invocando al AuthenticationManager
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    /**
     * Realiza acciones adicionales cuando la autenticación es exitosa.
     *
     * @param request     La solicitud HTTP entrante.
     * @param response    La respuesta HTTP.
     * @param filterChain El FilterChain para invocar los filtros restantes en la cadena.
     * @param authResult  El objeto Authentication que representa la autenticación exitosa.
     * @throws IOException      Si ocurre un error de entrada o salida.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        // Crear un nuevo token JWT utilizando el nombre, correo electrónico y roles del usuario autenticado
        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername(), userDetails.getAuthorities());

        // Agregar el token JWT y el rol del usuario como encabezados de la respuesta
        response.addHeader("Authorization", token);
        response.addHeader("Role", " " + userDetails.getAuthorities());

        // Vaciar el buffer de salida de la respuesta
        response.getWriter().flush();

        // Invocar al método successfulAuthentication del padre para continuar con la cadena de filtros
        super.successfulAuthentication(request, response, filterChain, authResult);
    }

    /**
     * Realiza acciones adicionales cuando la autenticación falla.
     *
     * @param request  La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param failed   La excepción que representa la autenticación fallida.
     * @throws IOException      Si ocurre un error de entrada o salida.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // Establecer el código de estado de la respuesta en 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Escribir el mensaje de error en el cuerpo de la respuesta
        response.getWriter().write("Authentication failed");
    }
}