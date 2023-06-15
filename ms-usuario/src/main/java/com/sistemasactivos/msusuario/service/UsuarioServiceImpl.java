package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.Usuario;
import com.sistemasactivos.msusuario.repository.BaseRepository;
import com.sistemasactivos.msusuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> {
    public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
    }

    public Optional<Usuario> findByEmail(String email) {
        return ((UsuarioRepository) baseRepository).findOneByEmail(email);
    }
}
