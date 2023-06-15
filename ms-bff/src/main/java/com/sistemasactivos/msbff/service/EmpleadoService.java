package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.Empleado;
import com.sistemasactivos.msbff.utils.StatusCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmpleadoService implements IEmpleadoService {
    @Autowired
    @Qualifier("empleadosWebClient")
    private WebClient webClient;

    @Autowired
    private CacheService cacheService;

    public Flux<Empleado> findAll() {
        return webClient.get()
                .uri("/empleados")
                .retrieve()
                .bodyToFlux(Empleado.class);
    }

    public Mono<Empleado> findById(Long id) {
        return webClient.get()
                .uri("/empleados/" + id)
                .retrieve()
                .bodyToMono(Empleado.class);
    }

    public Mono<Empleado> save(Empleado persona) {
        return webClient.post()
                .uri("/empleados")
                .bodyValue(persona)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, Empleado.class));
    }

    public Mono<Void> delete(Long id) {
        return webClient.delete()
                .uri("/empleados/" + id)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, Void.class));
    }

    public Mono<Empleado> update(Long id, Empleado persona) {
        return webClient.put()
                .uri("/empleados/" + id)
                .bodyValue(persona)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, Empleado.class));
    }

    public boolean isAdmin(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("token");
        String role = (String) cacheService.getDataFromCache(token);
        return role != null && role.equals("[admin]");
    }

    public boolean isUsuario(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("token");
        String role = (String) cacheService.getDataFromCache(token);
        return role != null && role.equals("[usuario]");
    }
}
