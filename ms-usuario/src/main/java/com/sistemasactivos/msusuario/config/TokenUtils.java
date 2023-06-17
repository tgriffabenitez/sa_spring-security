package com.sistemasactivos.msusuario.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilidades para la manipulación de tokens JWT.
 */
public class TokenUtils {
    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private final static Long ACCES_TOCKEN_LIFE = 60L; // 60 segundos

    /**
     * Crea un token de acceso con los datos proporcionados.
     *
     * @param name        El nombre del usuario.
     * @param email       El correo electrónico del usuario.
     * @param authorities Los roles y permisos del usuario.
     * @return El token de acceso generado.
     */
    public static String createToken(String name, String email, Collection<? extends GrantedAuthority> authorities) {
        // Calcula la fecha de expiración sumando el tiempo de expiración al tiempo actual
        long expirationTime = ACCES_TOCKEN_LIFE * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // Mapa para almacenar claims adicionales en el token
        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);

        // Agrega los roles al mapa de claims adicionales
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        extra.put("roles", roles);
        extra.put("exp", expirationDate.getTime() / 1000);

        // Construye el token JWT utilizando la biblioteca Jwts
        return Jwts.builder()
                .setSubject(email) // Establece el sujeto del token como el correo electrónico
                .setExpiration(expirationDate) // Establece la fecha de expiración del token
                .addClaims(extra) // Agrega los claims adicionales al token
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())) // Firma el token con la clave secreta
                .compact(); // Devuelve el token en formato compacto
    }

    /**
     * Obtiene la autenticación a partir de un token.
     *
     * @param token El token de acceso.
     * @return La autenticación obtenida a partir del token, o null si el token es inválido.
     */
    public static UsernamePasswordAuthenticationToken getAutentication(String token) throws Exception {
        String email;

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            email = claims.getSubject();

            // Obtiene los roles del claim "roles" en el mapa de claims extras
            List<String> roles = (List<String>) claims.get("roles");

            // Crea una lista de GrantedAuthority a partir de los roles
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Devuelve una instancia de UsernamePasswordAuthenticationToken con el correo electrónico como principal
            // y la lista de autoridades correspondiente a los roles
            return new UsernamePasswordAuthenticationToken(email, null, authorities);

        } catch (JwtException e) {
            throw new Exception("Token inválido", e.getCause());
        }
    }
}