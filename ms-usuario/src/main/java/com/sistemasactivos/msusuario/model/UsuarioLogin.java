package com.sistemasactivos.msusuario.model;

import lombok.Data;

/**
 * Clase que representa los datos de inicio de sesion de un usuario.
 */
@Data
public class UsuarioLogin {
    private String email;
    private String password;
}