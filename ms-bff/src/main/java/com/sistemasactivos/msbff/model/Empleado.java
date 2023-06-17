package com.sistemasactivos.msbff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


/**
 * Clase que representa un objeto Empleado con sus atributos correspondientes.
 * Implementa la interfaz Serializable para permitir su serialización.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String pais;
    private String cargo;
    private String salario;
}
