/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.CursosRepository;
import com.ispi.projectoIspi.model.Cursos;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class CursosService {

    @Autowired
    private CursosRepository cursosRepository;

    public List<Cursos> getAll() {
        return (List<Cursos>) cursosRepository.findAll();
    }

    public Page<Cursos> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return cursosRepository.findAll(pageable);
    }

    public Optional<Cursos> getOne(Long codigo) {
        return cursosRepository.findById(codigo);

    }

    public void addNew(Cursos curso) {
        Optional<Cursos> nameCurso = cursosRepository.findByNomeIgnoreCase(curso.getNome());
        if (nameCurso.isPresent() && !nameCurso.get().equals(curso)) {
            throw new NomeExistenteException("O curso já se encontra registado!");
        }
        cursosRepository.save(curso);
    }

    public void update(Cursos curso) {
        cursosRepository.save(curso);

    }

    public void delete(Long codigo) {
        cursosRepository.deleteById(codigo);

    }

}
