/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.NotaAlunoRepository;
import com.ispi.projectoIspi.model.Disciplina;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.NotaAluno;
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
public class NotaAlunoService {

    @Autowired
    private NotaAlunoRepository notaAlunoRepository;

    public List<NotaAluno> getAll() {
        return (List<NotaAluno>) notaAlunoRepository.findAll();
    }

    public Page<NotaAluno> findAll(int pageNumber) {
        Sort sort = Sort.by("disciplina.nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return notaAlunoRepository.findAll(pageable);
    }

    public Optional<NotaAluno> getOne(Long codigo) {
        return notaAlunoRepository.findById(codigo);

    }

    public void addNew(NotaAluno notaAluno) {
        Optional<NotaAluno> resultado = notaAlunoRepository.findByMatriculaAndDisciplina(notaAluno.getMatricula(), notaAluno.getDisciplina());
        if (resultado.isPresent() && !resultado.get().equals(notaAluno)) {
            throw new NomeExistenteException("As notas do aluno selecionado já se encontram registado!");
        }
        notaAlunoRepository.save(notaAluno);
    }

    public void update(NotaAluno notaAluno) {
        notaAlunoRepository.save(notaAluno);

    }

    public void delete(Long codigo) {
        notaAlunoRepository.deleteById(codigo);

    }

    public Page<NotaAluno> findAllAlunoNotas(Turma turma, Disciplina disciplina, int pageNumber) {
        Sort sort = Sort.by("matricula.aluno.nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return notaAlunoRepository.findByNotasAndTurmaAndDisciplina(turma, disciplina, pageable);
    }

    public List<NotaAluno> findByMatricula(Matricula matricula) {
        return notaAlunoRepository.findByMatricula(matricula);
    }
}
