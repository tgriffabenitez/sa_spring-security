package com.sistemasactivos.msempleados.repository;

import com.sistemasactivos.msempleados.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Interfaz base para repositorios de entidades.
 *
 * @param <E>  El tipo de entidad manejada por el repositorio.
 * @param <ID> El tipo de identificador de la entidad.
 */
@NoRepositoryBean
public interface BaseRepository <E extends Base, ID extends Serializable> extends JpaRepository<E, ID> {
}
