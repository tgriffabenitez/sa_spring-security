package com.sistemasactivos.msempleados.controller;

import com.sistemasactivos.msempleados.model.Empleado;
import com.sistemasactivos.msempleados.service.EmpleadoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para la entidad Empleado que expone endpoints RESTful para operaciones CRUD.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/empleados")
public class EmpleadoController extends BaseControllerImpl<Empleado, EmpleadoServiceImpl> {
}
