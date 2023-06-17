package com.sistemasactivos.msempleados.repository;

import com.sistemasactivos.msempleados.model.Empleado;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Empleado.
 */
@Repository
public interface EmpleadoRepository extends BaseRepository<Empleado, Long> {
}
