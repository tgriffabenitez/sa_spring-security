package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.Base;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaz base para servicios de entidades.
 *
 * @param <E>  Tipo de la entidad.
 * @param <ID> Tipo del ID de la entidad.
 */
public interface BaseService<E extends Base, ID extends Serializable> {

    /**
     * Recupera todos los elementos de la entidad.
     *
     * @return Una lista de elementos de la entidad.
     * @throws Exception si ocurre un error al recuperar los elementos.
     */
    List<E> findAll() throws Exception;

    /**
     * Recupera un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a recuperar.
     * @return El elemento encontrado, o null si no se encuentra ninguno.
     * @throws Exception si ocurre un error al recuperar el elemento.
     */
    E findById(ID id) throws Exception;

    /**
     * Guarda un elemento de la entidad.
     *
     * @param entity El elemento a guardar.
     * @return El elemento guardado.
     * @throws Exception si ocurre un error al guardar el elemento.
     */
    E save(E entity) throws Exception;

    /**
     * Actualiza un elemento de la entidad por su ID.
     *
     * @param id     El ID del elemento a actualizar.
     * @param entity El nuevo estado del elemento.
     * @return El elemento actualizado.
     * @throws Exception si ocurre un error al actualizar el elemento.
     */
    E update(ID id, E entity) throws Exception;

    /**
     * Elimina un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a eliminar.
     * @return true si el elemento se eliminó correctamente, false si no se encontró el elemento.
     * @throws Exception si ocurre un error al eliminar el elemento.
     */
    boolean delete(ID id) throws Exception;
}