package com.sistemasactivos.msusuario.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    /**
     * Filtra las solicitudes para la autorización basada en el token JWT.
     *
     * @param request     La solicitud HTTP entrante.
     * @param response    La respuesta HTTP.
     * @param filterChain El FilterChain para invocar los filtros restantes en la cadena.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException      Si ocurre un error de entrada o salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Extraer el token del encabezado y eliminar el prefijo "Bearer "
            String token = bearerToken.replace("Bearer ", "");

            // Obtener la autenticación de usuario a partir del token
            UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAutentication(token);

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}