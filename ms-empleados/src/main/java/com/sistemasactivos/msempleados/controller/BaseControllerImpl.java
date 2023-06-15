package com.sistemasactivos.msempleados.controller;

import com.sistemasactivos.msempleados.model.Base;
import com.sistemasactivos.msempleados.service.BaseServiceImpl;
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
     * Obtiene todos los elementos de la entidad.
     *
     * @return ResponseEntity con la lista de elementos si se encuentran, o un ResponseEntity con código 204 si no hay elementos.
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
     * Obtiene un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a buscar.
     * @return ResponseEntity con el elemento encontrado si existe, o un ResponseEntity con código 404 si no se encuentra.
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
     * Guarda un nuevo elemento de la entidad.
     *
     * @param entity El elemento a guardar.
     * @return ResponseEntity con el elemento guardado si se guarda correctamente, o un ResponseEntity con código 409 si hay conflicto.
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
     * Actualiza un elemento de la entidad por su ID.
     *
     * @param id     El ID del elemento a actualizar.
     * @param entity El nuevo estado del elemento.
     * @return ResponseEntity con el elemento actualizado si se actualiza correctamente, o un ResponseEntity con código 404 si no se encuentra el elemento o código 409 si hay conflicto.
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
     * Elimina un elemento de la entidad por su ID.
     *
     * @param id El ID del elemento a eliminar.
     * @return ResponseEntity con código 204 si se elimina correctamente, o un ResponseEntity con código 404 si no se encuentra el elemento o código 409 si hay conflicto.
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