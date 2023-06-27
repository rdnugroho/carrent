package id.co.indivara.jdt12.miniproject.controller;

import id.co.indivara.jdt12.miniproject.utilize.response.ResponseMessage;
import id.co.indivara.jdt12.miniproject.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GenericController<T> {
    @Autowired
    private GenericService<T> service;

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return (ResponseEntity<T>) new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND, "Data Tidak Ditemukan"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<T> insert(@RequestBody T entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entity) {
        try {
            return new ResponseEntity<>(service.update(id, entity), HttpStatus.CREATED);
        }
        catch (Exception e) {
            return (ResponseEntity<T>) new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND, "Data Tidak Ditemukan"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.CREATED, "Data Berhasil Dihapus"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST, "Data Tidak Ada, Data Gagal Dihapus"), HttpStatus.BAD_REQUEST);
        }
    }
}
