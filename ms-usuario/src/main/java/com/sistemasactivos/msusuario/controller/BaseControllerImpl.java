package com.sistemasactivos.msusuario.controller;

import com.sistemasactivos.msusuario.model.Base;
import com.sistemasactivos.msusuario.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseControllerImpl <E extends Base, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {

    @Autowired
    protected S service;

    /**
     * Obtiene todas las entidades.
     *
     * @return ResponseEntity con la lista de entidades si se encuentran, o un ResponseEntity vacío si no hay contenido.
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<E> entity = service.findAll();
            if (entity.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(entity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una entidad por su ID.
     *
     * @param id ID de la entidad a buscar.
     * @return ResponseEntity con la entidad si se encuentra, o un ResponseEntity con estado HTTP 404 (Not Found) si no se encuentra.
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            E entity = service.findById(id);
            if (entity == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(entity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Guarda una nueva entidad.
     *
     * @param entity Entidad a guardar.
     * @return ResponseEntity con la entidad guardada y estado HTTP 201 (Created) si se guarda correctamente.
     *         Si la entidad es nula, se devuelve un ResponseEntity con estado HTTP 400 (Bad Request).
     *         Si hay una violación de integridad de datos al guardar la entidad, se devuelve un ResponseEntity con estado HTTP 409 (Conflict).
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity) {
        try {
            if (entity == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            E entityDB = service.save(entity);
            return new ResponseEntity<>(entityDB, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza una entidad existente.
     *
     * @param id     ID de la entidad a actualizar.
     * @param entity Entidad actualizada.
     * @return ResponseEntity con la entidad actualizada y estado HTTP 200 (OK) si se actualiza correctamente.
     *         Si el ID es nulo o menor o igual a cero, se devuelve un ResponseEntity con estado HTTP 400 (Bad Request).
     *         Si no se encuentra la entidad con el ID especificado, se devuelve un ResponseEntity con estado HTTP 404 (Not Found).
     *         Si hay una violación de integridad de datos al actualizar la entidad, se devuelve un ResponseEntity con estado HTTP 409 (Conflict).
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entity) {
        try {
            if (id == null || id <= 0)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (service.findById(id) == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            E updatedEntity = service.update(id, entity);
            if (updatedEntity == null)
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>(updatedEntity, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una entidad por su ID.
     *
     * @param id ID de la entidad a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content) si la entidad se elimina correctamente.
     *         Si el ID es nulo o menor o igual a cero, se devuelve un ResponseEntity con estado HTTP 400 (Bad Request).
     *         Si no se encuentra la entidad con el ID especificado, se devuelve un ResponseEntity con estado HTTP 404 (Not Found).
     *         Si ocurre un error al eliminar la entidad, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (id == null || id <= 0)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (service.findById(id) == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            boolean deleted = service.delete(id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}