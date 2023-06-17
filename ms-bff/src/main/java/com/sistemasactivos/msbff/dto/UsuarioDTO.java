package com.sistemasactivos.msbff.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;


/**
 * Clase que representa un objeto Usuario con sus atributos correspondientes.
 * Implementa la interfaz Serializable para permitir su serialización.
 */
@Getter
@Setter
public class UsuarioDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String nombre;
    private Set<RolDTO> roles;
}
