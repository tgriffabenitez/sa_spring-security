package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.Empleado;
import com.sistemasactivos.msbff.utils.StatusCodeHandler;
import com.sistemasactivos.msbff.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
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

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Obtiene todos los empleados.
     *
     * @return Un flujo de empleados.
     */
    public Flux<Empleado> findAll() {
        return webClient.get()
                .uri("/empleados")
                .retrieve()
                .bodyToFlux(Empleado.class);
    }

    /**
     * Obtiene un empleado por su id.
     *
     * @param id El id del empleado.
     * @return Un empleado.
     */
    public Mono<Empleado> findById(Long id) {
        return webClient.get()
                .uri("/empleados/" + id)
                .retrieve()
                .bodyToMono(Empleado.class);
    }

    /**
     * Guarda un empleado.
     *
     * @param persona El empleado a guardar.
     * @return El empleado guardado.
     */
    public Mono<Empleado> save(Empleado persona) {
        return webClient.post()
                .uri("/empleados")
                .bodyValue(persona)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, Empleado.class));
    }

    /**
     * Elimina un empleado.
     *
     * @param id El id del empleado a eliminar.
     * @return Un mono vacío.
     */
    public Mono<Void> delete(Long id) {
        return webClient.delete()
                .uri("/empleados/" + id)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, Void.class));
    }

    /**
     * Actualiza un empleado.
     *
     * @param id      El id del empleado a actualizar.
     * @param persona El empleado con los datos actualizados.
     * @return El empleado actualizado.
     */
    public Mono<Empleado> update(Long id, Empleado persona) {
        return webClient.put()
                .uri("/empleados/" + id)
                .bodyValue(persona)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, Empleado.class));
    }

    /**
     * Verifica si el usuario tiene el rol de administrador.
     *
     * @param request La solicitud HTTP del cliente que contiene el token de acceso.
     * @return `true` si el usuario tiene el rol de administrador, de lo contrario `false`.
     */
    public boolean isAdmin(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = tokenUtils.extractTokenFromHeaders(headers);
        return token != null && tokenUtils.validateToken(token) && tokenUtils.validateRoles(token, "admin");
    }

    /**
     * Verifica si el usuario tiene el rol de usuario.
     *
     * @param request La solicitud HTTP del cliente que contiene el token de acceso.
     * @return `true` si el usuario tiene el rol de usuario, de lo contrario `false`.
     */
    public boolean isUsuario(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = tokenUtils.extractTokenFromHeaders(headers);
        return token != null && tokenUtils.validateToken(token) && tokenUtils.validateRoles(token, "usuario");
    }
}
