package id.co.indivara.jdt12.miniproject.service;

import id.co.indivara.jdt12.miniproject.repo.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
public class GenericService<T> {
    @Autowired
    GenericRepository<T> repository;

    public List<T> getAll(){
        return (List<T>) repository.findAll();
    }
    public T save(T entity){
        return repository.save(entity);
    }
    public T update(Long id, T entity){
        T newEntity = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Data yang anda cari tidak ditemukkan"));
        return repository.save(entity);
    }
    public T findById(Long id){
        return repository.findById(id).orElseThrow(()-> new NoSuchElementException("Data yang anda cari tidak ditemukkan"));
    }
    public void delete(Long id){
        T newEntity = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Data yang anda cari tidak ditemukkan"));
        repository.deleteById(id);
    }
}
