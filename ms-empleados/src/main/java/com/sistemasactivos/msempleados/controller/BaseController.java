package com.sistemasactivos.msempleados.controller;

import com.sistemasactivos.msempleados.model.Base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BaseController <E extends Base, ID extends Serializable> {
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(@PathVariable ID id);
    ResponseEntity<?> save(@RequestBody E entity);
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);
    ResponseEntity<?> delete(@PathVariable ID id);
}