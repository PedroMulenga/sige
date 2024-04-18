/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.CursosRepository;
import com.ispi.projectoIspi.Repository.DisciplinaRepository;
import com.ispi.projectoIspi.model.Cursos;
import com.ispi.projectoIspi.model.Disciplina;
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
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> getAll() {
        return (List<Disciplina>) disciplinaRepository.findAll();
    }

    public Page<Disciplina> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return disciplinaRepository.findAll(pageable);
    }

    public Optional<Disciplina> getOne(Long codigo) {
        return disciplinaRepository.findById(codigo);

    }

    public void addNew(Disciplina disciplina) {
        Optional<Disciplina> nameDisciplina = disciplinaRepository.findByNomeIgnoreCase(disciplina.getNome());
        if (nameDisciplina.isPresent() && !nameDisciplina.get().equals(disciplina)) {
            throw new NomeExistenteException("A disciplina curso já se encontra registado!");
        }
        disciplinaRepository.save(disciplina);
    }

    public void update(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);

    }

    public void delete(Long codigo) {
        disciplinaRepository.deleteById(codigo);

    }

}
