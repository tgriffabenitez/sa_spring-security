package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.UsuarioRegister;
import org.springframework.stereotype.Service;

/**
 * Interfaz que define el servicio de registro de usuarios.
 */
public interface UsuarioRegisterService {

    /**
     * Registra un nuevo usuario.
     *
     * @param userRegistration Los datos de registro del usuario.
     * @return true si el usuario se registra correctamente, false en caso contrario.
     * @throws Exception si ocurre un error durante el registro del usuario.
     */
    boolean registerUser(UsuarioRegister userRegistration) throws Exception;
}