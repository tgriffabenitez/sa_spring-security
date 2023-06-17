package com.sistemasactivos.msbff.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsuarioDTO {
    private String email;
    private String nombre;
    private Set<RolDTO> roles;
}
