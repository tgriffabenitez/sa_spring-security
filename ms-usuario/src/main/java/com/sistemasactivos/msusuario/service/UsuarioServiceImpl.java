package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.Usuario;
import com.sistemasactivos.msusuario.repository.BaseRepository;
import org.springframework.stereotype.Service;


/**
 * Implementación del servicio de usuarios.
 */
@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> {

    /**
     * Constructor de UsuarioServiceImpl.
     *
     * @param baseRepository El repositorio base para la entidad Usuario.
     */
    public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
    }

}
