package com.sistemasactivos.msusuario.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemasactivos.msusuario.utils.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autorización JWT que extiende OncePerRequestFilter.
 * Este filtro se utiliza para autorizar las solicitudes basadas en el token JWT.
 */
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
        // Obtengo el token del header de la solicitud
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Al token y le saco el prefijo "Bearer "
            String token = bearerToken.replace("Bearer ", "");

            // Obtengo el rol del usuario
            String role = request.getHeader("Role");

            try {
                // Obtengo la autenticación de usuario a partir del token
                UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAutentication(token);

                // Verifico que el usuario tenga el rol necesario para acceder al recurso
                // el getAuthorities() devuelve una lista de GrantedAuthority con los roles del usuario
                if (usernamePAT.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role))) {
                    SecurityContextHolder.getContext().setAuthentication(usernamePAT);
                } else {
                    ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a este recurso.");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                    return;
                }
            } catch (Exception e) {
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                return;
            }
        }

        // Continuo con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}