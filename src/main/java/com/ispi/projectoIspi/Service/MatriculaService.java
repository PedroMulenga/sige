/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.MatriculaRepository;
import com.ispi.projectoIspi.model.Aluno;
import com.ispi.projectoIspi.model.Matricula;
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
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public List<Matricula> getAll() {
        return (List<Matricula>) matriculaRepository.findAll();
    }

    public Optional<Matricula> getOne(Long codigo) {
        return matriculaRepository.findById(codigo);

    }

    public Matricula findByNumeroEstudante(Long numeroEstudante) {
        return matriculaRepository.findByCodigo(numeroEstudante);

    }

    public Matricula findById(Long codigo) {
        return matriculaRepository.findByCodigo(codigo);

    }

    public void addNew(Matricula matricula) {
        Optional<Matricula> matriculaExistente = matriculaRepository.findByAlunoAndAnoLectivo(matricula.getAluno(), matricula.getAnoLectivo());

        if (matriculaExistente.isPresent() && !matriculaExistente.get().equals(matricula)) {
            System.out.println("Matricula" + matricula.getCodigo());
            throw new EntidadeUsuarioExistenteException("O Aluno já se encontra Registado para o Ano Lectivo Informado!");
        }
        matriculaRepository.save(matricula);
    }

    public void update(Matricula matricula) {
        matriculaRepository.save(matricula);
    }

    public void delete(Long codigo) {
        matriculaRepository.deleteById(codigo);

    }

    public Page<Matricula> findByTurma(Turma turma, int pageNumber) {
        Sort sort = Sort.by("aluno").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return matriculaRepository.findByTurmaAndAnoLectivoIsTrue(turma, pageable);
    }

    public List<Matricula> findByTurma(Turma turma) {
        return (List<Matricula>) matriculaRepository.findByTurmaAndAnoLectivo(turma);
    }

    public Matricula findByNumeroEstudanteAndSituacao(Long numEstudante, SituacaoMatricula situacao) {
        Matricula matricula = matriculaRepository.findByCodigoAndSituacao(numEstudante, situacao.MATRICULADO);
        return matricula;
    }

}
