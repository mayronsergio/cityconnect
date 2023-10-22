package com.codelogic.cityconnect.service;

import com.codelogic.cityconnect.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class GenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public List<T> listarTodos() {
        return repository.findAll();
    }

    public Optional<T> buscarPorId(ID id) {
        return repository.findById(id);
    }

    public T salvar(T entidade) {
        return repository.save(entidade);
    }

    public void deletarPorId(ID id) {
        Optional<T> objetoOptional = buscarPorId(id);

        if (objetoOptional.isPresent()) {
            T objeto = objetoOptional.get();
            repository.delete(objeto);
        } else {
            throw new ResourceNotFoundException("Objeto com ID " + id + " não encontrado.");
        }
    }

}
