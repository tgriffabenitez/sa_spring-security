package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.dto.EmpleadoDTO;
import com.sistemasactivos.msbff.utils.StatusCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para el manejo de empleados.
 */
@Service
public class EmpleadoService implements IEmpleadoService {
    @Autowired
    @Qualifier("empleadosWebClient")
    private WebClient webClient;

    /**
     * Obtiene todos los empleados.
     *
     * @return Un flujo de empleados.
     */
    public Flux<EmpleadoDTO> findAll() {
        return webClient.get()
                .uri("/empleados")
                .retrieve()
                .bodyToFlux(EmpleadoDTO.class);
    }

    /**
     * Obtiene un empleado por su id.
     *
     * @param id El id del empleado.
     * @return Un empleado.
     */
    public Mono<EmpleadoDTO> findById(Long id) {
        return webClient.get()
                .uri("/empleados/" + id)
                .retrieve()
                .bodyToMono(EmpleadoDTO.class);
    }

    /**
     * Guarda un empleado.
     *
     * @param persona El empleado a guardar.
     * @return El empleado guardado.
     */
    public Mono<EmpleadoDTO> save(EmpleadoDTO persona) {
        return webClient.post()
                .uri("/empleados")
                .bodyValue(persona)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, EmpleadoDTO.class));
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
    public Mono<EmpleadoDTO> update(Long id, EmpleadoDTO persona) {
        return webClient.put()
                .uri("/empleados/" + id)
                .bodyValue(persona)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, EmpleadoDTO.class));
    }
}
