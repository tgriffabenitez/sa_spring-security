package com.sistemasactivos.msbff.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


/*
 * La idea de esta clase es poder manejar los codigos de errores en la capa de servicios.
 * Los ms devuelven codigos de estados variados, y en el bff no encontre una forma facil
 * de discrimarlos.
 *
 * En esta misma carpeta utils, hay un enum que se llama StatusCode donde estan todos los
 * posibles errores que puse en los controllers de los microservicios.
 *
 * Esta clase es generica, no depende del tipo de dato que se maneje en el microservicio.
 *
 * No encontre la discriminar los codigos 2xx, es por eso que cualquier codigo que sea del
 * tipo 2xx van a aparecer como un 200. Esto esta mal, pero no encontre la forma.
 */
public class StatusCodeHandler {

    /**
     * Maneja la respuesta del cliente y devuelve un Mono que contiene el objeto deseado si la respuesta es exitosa.
     * En caso de errores, se lanza una excepción ResponseStatusException.
     *
     * @param clientResponse La respuesta del cliente obtenida al realizar una solicitud HTTP.
     * @param clazz          La clase del objeto deseado.
     * @param <T>            El tipo de objeto deseado.
     * @return Un Mono que contiene el objeto deseado si la respuesta es exitosa.
     * @throws ResponseStatusException Si ocurre un error en la respuesta del cliente.
     * @throws RuntimeException       Si ocurre un error desconocido.
     */
    public static <T> Mono<T> clientResponse(ClientResponse clientResponse, Class<T> clazz) {
        HttpStatusCode statusCode = clientResponse.statusCode();
        if (statusCode.is2xxSuccessful()) {
            return clientResponse.bodyToMono(clazz);

        } else if (statusCode.is4xxClientError()) {
            // recorro el enum StatusCode y busco el codigo de error que me devolvio el ms
            for (StatusCode errorCode : StatusCode.values()) {
                if (statusCode.equals(errorCode.getHttpStatus())) {
                    return Mono.error(new ResponseStatusException(errorCode.getHttpStatus(), errorCode.getErrorMessage()));
                }
            }
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        } else {
            return Mono.error(new RuntimeException("Error"));
        }
    }
}