package com.sistemasactivos.msusuario.repository;

import com.sistemasactivos.msusuario.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Rol.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre.
     *
     * @param nombre El nombre del rol a buscar.
     * @return Una instancia de Optional que contiene el rol encontrado, o vacío si no se encuentra.
     */
    Optional<Rol> findByNombre(String nombre);
}
