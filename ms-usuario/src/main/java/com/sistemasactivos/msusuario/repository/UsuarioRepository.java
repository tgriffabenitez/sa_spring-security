package com.sistemasactivos.msusuario.repository;

import com.sistemasactivos.msusuario.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Optional<Usuario> findOneByEmail(String email);
}
