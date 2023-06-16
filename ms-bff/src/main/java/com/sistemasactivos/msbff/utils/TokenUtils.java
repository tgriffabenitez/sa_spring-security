package com.sistemasactivos.msbff.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Clase utilitaria para el manejo del token.
 */
@Component
public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";

    /**
     * Valida el token de acceso utilizando la clave secreta con la que fue firmado.
     *
     * @param token El token de acceso.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes()) // Configura la clave secreta para validar la firma del token
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException e) {
            throw new JwtException("El token no es valido.");
        }
    }

    /**
     * Verifica si un token ha expirado.
     *
     * @param token el token a verificar.
     * @return {@code true} si el token ha expirado, {@code false} en caso contrario.
     * @throws JwtException si ocurre un error al validar el token.
     */
    public boolean isTokenExpired(String token) throws JwtException {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);

            Date expirationDate = claimsJws.getBody().getExpiration();
            Date currentDate = new Date();

            return expirationDate == null || !currentDate.before(expirationDate);
        } catch (JwtException e) {
            throw new JwtException("Token expirado. Inicie sesión nuevamente");

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
     * Extrae el token de autenticación de los encabezados.
     *
     * @param headers Los encabezados HTTP de la respuesta del servicio de inicio de sesión.
     * @return El token de autenticación o null si no se encuentra.
     */
    public String getTokenFromHeaders(HttpHeaders headers) {
        List<String> authorizationValues = headers.get("Authorization");
        if (authorizationValues != null && !authorizationValues.isEmpty()) {
            String bearerToken = authorizationValues.get(0);
            return bearerToken.replace("Bearer ", "").trim();
        }
        return null;
    }
}