package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.UsuarioRegister;
import org.springframework.stereotype.Service;

public interface UsuarioRegisterService {
    boolean registerUser(UsuarioRegister userRegistration) throws Exception;
}
