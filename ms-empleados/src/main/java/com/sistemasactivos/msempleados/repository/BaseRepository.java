package com.sistemasactivos.msempleados.repository;

import com.sistemasactivos.msempleados.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository <E extends Base, ID extends Serializable> extends JpaRepository<E, ID> {
    Optional<E> findByNombre(String nombre);
    Optional<E> findByEmail(String email);
}
