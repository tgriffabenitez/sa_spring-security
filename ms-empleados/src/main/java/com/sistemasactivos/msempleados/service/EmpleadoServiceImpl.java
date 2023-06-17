package com.sistemasactivos.msempleados.service;

import com.sistemasactivos.msempleados.model.Empleado;
import com.sistemasactivos.msempleados.repository.BaseRepository;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de empleados.
 */
@Service
public class EmpleadoServiceImpl extends BaseServiceImpl<Empleado, Long> {

    /**
     * Constructor de EmpleadoServiceImpl.
     *
     * @param baseRepository Repositorio base para la entidad Empleado.
     */
    public EmpleadoServiceImpl(BaseRepository<Empleado, Long> baseRepository) {
        super(baseRepository);
    }
}