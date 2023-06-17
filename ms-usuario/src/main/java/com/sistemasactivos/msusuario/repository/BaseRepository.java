package com.sistemasactivos.msusuario.repository;

import com.sistemasactivos.msusuario.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

/**
 * Interfaz base para repositorios.
 *
 * @param <E>  El tipo de entidad manejada por el repositorio.
 * @param <ID> El tipo de identificador de la entidad.
 */
@NoRepositoryBean
public interface BaseRepository<E extends Base, ID extends Serializable> extends JpaRepository<E, ID> {
    /**
     * Busca una entidad por su correo electrónico.
     *
     * @param email El correo electrónico de la entidad a buscar.
     * @return Un Optional que contiene la entidad encontrada, o vacío si no se encuentra ninguna.
     */
    Optional<E> findByEmail(String email);
}