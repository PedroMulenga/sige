/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.ProfessorTurmaRepository;
import com.ispi.projectoIspi.model.Funcionario;
import com.ispi.projectoIspi.model.ProfessorTurma;
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
public class ProfessorTurmaService {

    @Autowired
    private ProfessorTurmaRepository professorTurmaRepository;

    public List<ProfessorTurma> getAll() {
        return (List<ProfessorTurma>) professorTurmaRepository.findAll();
    }

    public Page<ProfessorTurma> findAll(int pageNumber) {
        Sort sort = Sort.by("funcionario").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return professorTurmaRepository.findAll(pageable);
    }

    public ProfessorTurma getOne(Long codigo) {
        return professorTurmaRepository.getOne(codigo);

    }

    public void addNew(ProfessorTurma professorTurma) {
        Optional<ProfessorTurma> professor = professorTurmaRepository.findByFuncionarioAndTurmaAndDisciplinaAndAnoLectivo(professorTurma.getFuncionario(),
                professorTurma.getTurma(), professorTurma.getDisciplina(), professorTurma.getAnoLectivo());
        if (professor.isPresent() && !professor.get().equals(professorTurma)
                && professor.get().getDisciplina().equals(professorTurma.getDisciplina())
                && professor.get().getTurma().equals(professorTurma.getTurma())) {
            throw new NomeExistenteException("O Professor já se encontra registado nesta turma para esta disciplina, no Ano Lectivo Selecionado!");
        }
        professorTurmaRepository.save(professorTurma);
    }

    public void update(ProfessorTurma professorTurma) {
        professorTurmaRepository.save(professorTurma);

    }

    public void delete(Long codigo) {
        professorTurmaRepository.deleteById(codigo);

    }

    public Page<ProfessorTurma> findByFuncionarioAndAnoLectivo(Funcionario funcionario, int pageNumber) {
        Sort sort = Sort.by("disciplina").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return (Page<ProfessorTurma>) professorTurmaRepository.findByFuncionarioAndAnoLectivo(funcionario, pageable);
    }

    public Page<ProfessorTurma> findByTurma(Turma turma, int pageNumber) {
        Sort sort = Sort.by("disciplina").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return (Page<ProfessorTurma>) professorTurmaRepository.findByTurma(turma, pageable);
    }
}
