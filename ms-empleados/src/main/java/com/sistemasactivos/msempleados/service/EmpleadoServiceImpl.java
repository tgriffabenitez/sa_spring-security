package com.sistemasactivos.msempleados.service;

import com.sistemasactivos.msempleados.model.Empleado;
import com.sistemasactivos.msempleados.repository.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl extends BaseServiceImpl<Empleado, Long> {
    public EmpleadoServiceImpl(BaseRepository<Empleado, Long> baseRepository) {
        super(baseRepository);
    }
}
