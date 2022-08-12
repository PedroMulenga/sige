/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.TurmasRepository;
import com.ispi.projectoIspi.model.Turma;
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
public class TurmaService {

    @Autowired
    private TurmasRepository turmasRepository;

    public List<Turma> getAll() {
        return (List<Turma>) turmasRepository.findAll();
    }

    public Page<Turma> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return turmasRepository.findAll(pageable);
    }

    public Turma getOne(Long codigo) {
        return turmasRepository.findById(codigo).orElse(null);

    }

    public void addNew(Turma turma) {
        Optional<Turma> nomeTurma = turmasRepository.findByNomeIgnoreCase(turma.getNome());
        if (nomeTurma.isPresent() && !nomeTurma.get().equals(turma)) {
            throw new NomeExistenteException("Turma já cadastrada");
        }
        turmasRepository.save(turma);
    }

    public void update(Turma turma) {
        turmasRepository.save(turma);

    }

    public void delete(Long codigo) {
        turmasRepository.deleteById(codigo);

    }

}
