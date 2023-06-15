package com.sistemasactivos.msbff.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusCode {

    // codigos de error
    NOT_FOUND(HttpStatus.NOT_FOUND, "No se existe el recurso"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Solicitud incorrecta"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "No se encontro el recurso"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"),
    CONFLICT(HttpStatus.CONFLICT, "Conflicto"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "No autorizado"),

    // codigos de exito
    OK(HttpStatus.OK, "OK"),
    CREATED(HttpStatus.CREATED, "Creado"),
    ;

    private HttpStatus httpStatus;
    private String errorMessage;
}