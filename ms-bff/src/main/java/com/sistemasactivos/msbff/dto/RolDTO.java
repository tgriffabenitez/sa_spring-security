package com.sistemasactivos.msbff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


/**
 * Clase que representa un objeto Rol con sus atributos correspondientes.
 * Implementa la interfaz Serializable para permitir su serialización.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
}
