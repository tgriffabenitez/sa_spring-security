package com.sistemasactivos.msusuario.repository;

import com.sistemasactivos.msusuario.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return Una instancia de Optional que contiene el usuario encontrado, o vacío si no se encuentra.
     */
    Optional<Usuario> findOneByEmail(String email);
}
