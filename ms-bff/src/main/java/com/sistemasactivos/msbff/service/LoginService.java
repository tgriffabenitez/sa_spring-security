package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
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

    @Override
    public Mono<?> login(LoginRequest loginRequest, ServerHttpResponse response) {

        return webClient.post()
                .uri("/login")
                .bodyValue(loginRequest)
                .exchange()
                .flatMap(clientResponse -> {
                    HttpHeaders headers = clientResponse.headers().asHttpHeaders();

                    // Obtengo el token del response y lo agrego al header del response
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
                });
    }
}
