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

    /**
     * Parsea un token de acceso y retorna los claims contenidos en él.
     *
     * @param token El token de acceso a parsear.
     * @return Los claims del token de acceso, o null si ocurre un error durante el parsing.
     */
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

    /**
     * Obtiene los roles contenidos en un token de acceso.
     *
     * @param token El token de acceso del cual se obtendrán los roles.
     * @return Una lista de roles contenidos en el token de acceso, o null si el token no es válido o no contiene roles.
     */
    public List<String> getRolesFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return (List<String>) claims.get("roles");
        }
        return null;
    }

    /**
     * Valida si un token de acceso contiene un rol específico.
     *
     * @param token        El token de acceso a validar.
     * @param requiredRole El rol requerido a verificar en el token.
     * @return true si el token contiene el rol requerido, false si el token no es válido o no contiene el rol.
     */
    public boolean validateRoles(String token, String requiredRole) {
        List<String> roles = getRolesFromToken(token);
        return roles != null && roles.contains(requiredRole);
    }
}