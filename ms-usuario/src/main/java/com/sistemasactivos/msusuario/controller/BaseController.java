package com.sistemasactivos.msusuario.controller;

import com.sistemasactivos.msusuario.model.Base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * Interfaz base para controladores RESTful.
 *
 * @param <E>  El tipo de entidad manejada por el controlador.
 * @param <ID> El tipo de identificador de la entidad.
 */
public interface BaseController<E extends Base, ID extends Serializable> {

    /**
     * Recupera todos los elementos de la entidad.
     *
     * @return Una ResponseEntity con la lista de elementos de la entidad.
     */
    ResponseEntity<?> getAll();

    /**
     * Recupera un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a recuperar.
     * @return Una ResponseEntity con el elemento encontrado, o un mensaje de error si no se encuentra ninguno.
     */
    ResponseEntity<?> getById(@PathVariable ID id);
}