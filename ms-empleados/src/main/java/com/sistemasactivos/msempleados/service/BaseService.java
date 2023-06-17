package com.sistemasactivos.msempleados.service;

import com.sistemasactivos.msempleados.model.Base;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaz base para los servicios.
 *
 * @param <E>  El tipo de entidad manejada por el servicio.
 * @param <ID> El tipo de identificador de la entidad.
 */
public interface BaseService<E extends Base, ID extends Serializable> {

    /**
     * Obtiene todos los elementos de la entidad.
     *
     * @return Una lista de elementos de la entidad.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    List<E> findAll() throws Exception;

    /**
     * Obtiene un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a buscar.
     * @return El elemento encontrado.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    E findById(ID id) throws Exception;

    /**
     * Guarda un nuevo elemento de la entidad.
     *
     * @param entity El elemento a guardar.
     * @return El elemento guardado.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    E save(E entity) throws Exception;

    /**
     * Actualiza un elemento de la entidad por su ID.
     *
     * @param id     El ID del elemento a actualizar.
     * @param entity El nuevo estado del elemento.
     * @return El elemento actualizado.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    E update(ID id, E entity) throws Exception;

    /**
     * Elimina un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a eliminar.
     * @return `true` si se elimina correctamente, o `false` si no se encuentra el elemento.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    boolean delete(ID id) throws Exception;
}