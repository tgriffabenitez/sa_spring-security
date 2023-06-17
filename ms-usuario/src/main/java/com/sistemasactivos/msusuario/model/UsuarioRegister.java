package com.sistemasactivos.msusuario.model;

import lombok.Data;

/**
 * Clase que representa los datos de registro de un usuario.
 */
@Data
public class UsuarioRegister {
    private String nombre;
    private String email;
    private String password;
}
