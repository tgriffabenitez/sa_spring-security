package com.sistemasactivos.msbff.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";

    /**
     * Valida el token de acceso utilizando la clave secreta con la que fue firmado.
     *
     * @param token El token de acceso.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes()) // Configura la clave secreta para validar la firma del token
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return (List<String>) claims.get("roles");
        }
        return null;
    }

    public boolean validateRoles(String token, String requiredRole) {
        List<String> roles = getRolesFromToken(token);
        return roles != null && roles.contains(requiredRole);
    }
}