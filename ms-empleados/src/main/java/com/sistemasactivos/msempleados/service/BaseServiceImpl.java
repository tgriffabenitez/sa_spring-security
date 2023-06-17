package com.sistemasactivos.msempleados.service;

import com.sistemasactivos.msempleados.model.Base;
import com.sistemasactivos.msempleados.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Implementación base para los servicios.
 *
 * @param <E>  El tipo de entidad manejada por el servicio.
 * @param <ID> El tipo de identificador de la entidad.
 */
public class BaseServiceImpl <E extends Base, ID extends Serializable> implements BaseService<E, ID> {

    protected BaseRepository<E, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * Recupera todos los elementos de la entidad.
     *
     * @return Una lista de elementos de la entidad.
     * @throws Exception si ocurre un error al recuperar los elementos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<E> findAll() throws Exception {
        try {
            return baseRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Recupera un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a recuperar.
     * @return El elemento encontrado, o null si no se encuentra ninguno.
     * @throws Exception si ocurre un error al recuperar el elemento.
     */
    @Override
    @Transactional(readOnly = true)
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Guarda un elemento de la entidad.
     *
     * @param entity El elemento a guardar.
     * @return El elemento guardado.
     * @throws Exception si ocurre un error al guardar el elemento.
     */
    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Actualiza un elemento de la entidad por su ID.
     *
     * @param id     El ID del elemento a actualizar.
     * @param entity El nuevo estado del elemento.
     * @return El elemento actualizado.
     * @throws Exception si ocurre un error al actualizar el elemento.
     */
    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Long idToUpdate;
            Optional<E> entityOptional = baseRepository.findById(id);
            if (entityOptional.isPresent()) {
                E entityToUpdate = entityOptional.get();
                 idToUpdate= entityToUpdate.getId();
            } else {
                throw new Exception("No existe un registro con el id ingresado");
            }
            entity.setId(idToUpdate);
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Elimina un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a eliminar.
     * @return true si el elemento se eliminó correctamente, false si no se encontró el elemento.
     * @throws Exception si ocurre un error al eliminar el elemento.
     */
    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            if (entityOptional.isPresent()) {
                baseRepository.deleteById(id);
                return true;
            } else {
                throw new Exception("No existe un registro con el id ingresado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
