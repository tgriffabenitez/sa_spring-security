package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.LoginRequest;
import com.sistemasactivos.msbff.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class LoginService implements ILoginService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    @Qualifier("signInWebClient")
    private WebClient webClient;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Realiza el proceso de inicio de sesión.
     *
     * @param loginRequest El objeto LoginRequest que contiene las credenciales de inicio de sesión.
     * @param response     La respuesta HTTP que se utilizará para establecer las cookies de autenticación.
     * @return Un Mono que emite el resultado del proceso de inicio de sesión.
     */
    @Override
    public Mono<?> login(LoginRequest loginRequest, ServerHttpResponse response) {

        return webClient.post()
                .uri("/login")
                .bodyValue(loginRequest)
                .exchange()
                .flatMap(clientResponse -> handleLoginResponse(clientResponse, response));
    }

    /**
     * Maneja la respuesta del servicio de inicio de sesión.
     *
     * @param clientResponse La respuesta del cliente al servicio de inicio de sesión.
     * @param response       La respuesta HTTP que se utilizará para establecer las cookies de autenticación.
     * @return Un Mono que emite el resultado del proceso de inicio de sesión.
     */
    private Mono<String> handleLoginResponse(ClientResponse clientResponse, ServerHttpResponse response) {
        HttpStatusCode statusCode = clientResponse.statusCode();

        if (!statusCode.is2xxSuccessful()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }

        // Obtengo el token del header
        HttpHeaders headers = clientResponse.headers().asHttpHeaders();
        String token = tokenUtils.extractTokenFromHeaders(headers);
        if (token == null || !tokenUtils.validateToken(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }

        // Obtengo el rol del header
        String userRol = tokenUtils.extractRoleFromHeaders(headers);
        if (userRol == null || !tokenUtils.validateRoles(token, userRol)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }

        // Agrego el token al header del response
        response.getHeaders().add("Bearer", "Bearer " + token);
        response.getHeaders().add("Roles", userRol);

        // Almaceno el token y el rol en la cache
        cacheService.putDataInCache(token, userRol);
        return clientResponse.bodyToMono(String.class);
    }
}