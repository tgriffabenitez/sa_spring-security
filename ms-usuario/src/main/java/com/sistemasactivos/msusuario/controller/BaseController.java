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
public interface BaseController <E extends Base, ID extends Serializable> {
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(@PathVariable ID id);
    ResponseEntity<?> save(@RequestBody E entity);
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);
    ResponseEntity<?> delete(@PathVariable ID id);
}