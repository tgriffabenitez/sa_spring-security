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

    /**
     * Guarda un elemento de la entidad.
     *
     * @param entity El elemento a guardar.
     * @return Una ResponseEntity con el elemento guardado, o un mensaje de error si ocurre un problema.
     */
    ResponseEntity<?> save(@RequestBody E entity);

    /**
     * Actualiza un elemento de la entidad por su ID.
     *
     * @param id     El ID del elemento a actualizar.
     * @param entity El nuevo estado del elemento.
     * @return Una ResponseEntity con el elemento actualizado, o un mensaje de error si ocurre un problema.
     */
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);

    /**
     * Elimina un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a eliminar.
     * @return Una ResponseEntity con el resultado de la eliminación, o un mensaje de error si ocurre un problema.
     */
    ResponseEntity<?> delete(@PathVariable ID id);
}