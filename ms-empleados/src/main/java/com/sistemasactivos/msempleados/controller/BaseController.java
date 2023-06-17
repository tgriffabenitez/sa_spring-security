package com.sistemasactivos.msempleados.controller;

import com.sistemasactivos.msempleados.model.Base;
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

    /**
     * Obtiene todos los elementos de la entidad.
     *
     * @return Una ResponseEntity que contiene la respuesta con la lista de elementos.
     */
    ResponseEntity<?> getAll();

    /**
     * Obtiene un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a recuperar.
     * @return Una ResponseEntity que contiene la respuesta con el elemento encontrado.
     */
    ResponseEntity<?> getById(@PathVariable ID id);

    /**
     * Guarda un elemento de la entidad.
     *
     * @param entity El elemento a guardar.
     * @return Una ResponseEntity que contiene la respuesta con el elemento guardado.
     */
    ResponseEntity<?> save(@RequestBody E entity);

    /**
     * Actualiza un elemento de la entidad por su ID.
     *
     * @param id     El ID del elemento a actualizar.
     * @param entity El nuevo estado del elemento.
     * @return Una ResponseEntity que contiene la respuesta con el elemento actualizado.
     */
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);

    /**
     * Elimina un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a eliminar.
     * @return Una ResponseEntity que contiene la respuesta indicando el resultado de la eliminación.
     */
    ResponseEntity<?> delete(@PathVariable ID id);
}