package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class LoginService implements ILoginService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    @Qualifier("signInWebClient")
    private WebClient webClient;

    private String userToken;
    private String userRol;

    /**
     * Realiza el proceso de inicio de sesión.
     *
     * @param loginRequest El objeto LoginRequest que contiene las credenciales de inicio de sesión.
     * @param response     La respuesta HTTP que se utilizará para establecer las cookies de autenticación.
     * @return Un Mono que emite el resultado del proceso de inicio de sesión.
     */
    @Override
    public Mono<?> login(LoginRequest loginRequest, ServerHttpResponse response) {
        // Solicitud POST al endpoint "/login" utilizando WebClient
        return webClient.post()
                .uri("/login")
                .bodyValue(loginRequest)
                .exchange()
                .flatMap(clientResponse -> {

                    // Codigo de estado de la respuesta
                    HttpStatusCode statusCode = clientResponse.statusCode();

                    if (statusCode.is2xxSuccessful()) {
                        HttpHeaders headers = clientResponse.headers().asHttpHeaders();

                        // Obtengo los valores del authorization header
                        List<String> authorizationValues = headers.get("Authorization");
                        if (authorizationValues != null && !authorizationValues.isEmpty()) {
                            userToken = authorizationValues.get(0);
                            response.getHeaders().add("Bearer", userToken);
                        }

                        // Obtengo el rol del response
                        List<String> roleValues = headers.get("Role");
                        if (roleValues != null && !roleValues.isEmpty()) {
                            userRol = roleValues.get(0);
                        }

                        // Guardo el token y el rol en la cache
                        cacheService.putDataInCache(userToken, userRol);
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        // El inicio de sesión no fue exitoso, establezco el código de estado en la respuesta
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return Mono.empty();
                    }
                });
    }
}
