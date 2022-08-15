/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.Genero;
import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.DataInvalidaException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NumeroEstudanteUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.MatriculaRepository;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.time.LocalDate;
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

    public Iterable<Matricula> findById(Long codigo) {
        return matriculaRepository.findByCodigo(codigo);

    }

    public void addNew(Matricula matricula) {
        Optional<Matricula> numeroEstudante = matriculaRepository.findByNumeroEstudante(matricula.getNumeroEstudante());
        // CÁLCULO DA IDADE DO ESTUDANTE UTILIZANDO LOCALDATE
        LocalDate anoSistema = LocalDate.now();
        int anoConvertido = anoSistema.getYear();
        int idadeCalculada = anoConvertido - matricula.getDataNascimento().getYear();
        //CONSULTAS REALIZADAS
        Optional<Matricula> emailOptional = matriculaRepository.findByEmailIgnoreCase(matricula.getEmail());
        Optional<Matricula> telefoneOptional = matriculaRepository.findByTelefone(matricula.getTelefone());
        Optional<Matricula> biOptional = matriculaRepository.findByBi(matricula.getBi().toUpperCase());
        if (emailOptional.isPresent() && !emailOptional.get().equals(matricula)) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado!");
        }
        if (telefoneOptional.isPresent() && !telefoneOptional.get().equals(matricula)) {
            throw new TelefoneUsuarioExistenteException("Este Telefone já se encontra registado!");
        }
        if (biOptional.isPresent() && !biOptional.get().equals(matricula)) {
            throw new BiUsuarioExistenteException("Este B.I já se encontra registado!");
        }
        if (idadeCalculada <= 0) {
            throw new DataInvalidaException("A Data informada é inválida");
        }
        if (!matricula.getNumCRM().equals("") && matricula.getGenero() == Genero.FEMENINO) {
            throw new NomeExistenteException("Este campo não pode ser preenchido para Mulheres");
        }
        if (numeroEstudante.isPresent() && !numeroEstudante.get().equals(matricula)) {
            throw new NumeroEstudanteUsuarioExistenteException("Nº de Estudante já cadastrado, Gerar outro!");
        }
        if (numeroEstudante.isPresent() && !numeroEstudante.get().equals(matricula)) {
            throw new EntidadeUsuarioExistenteException("O Estudante já se encontra cadastrado!");
        }
        matriculaRepository.save(matricula);
    }

    public void delete(Long codigo) {
        matriculaRepository.deleteById(codigo);

    }

    public Page<Matricula> findByTurma(Turma turma, int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return matriculaRepository.findByTurma(turma, pageable);
    }

    public List<Matricula> findByTurma(Turma turma) {
        return (List<Matricula>) matriculaRepository.findByTurma(turma);
    }

    public Matricula findByNumeroEstudanteAndSituacao(String numeroEstudante, SituacaoMatricula situacao) {
        Matricula matricula = matriculaRepository.findByNumeroEstudanteAndSituacao(numeroEstudante, situacao.MATRICULADO);
        return matricula;
    }

    public Matricula findByBiEstudanteAndSituacao(String bi, String numeroEstudante) {
        Matricula matricula = matriculaRepository.findByBiEstudanteAndSituacao(bi, numeroEstudante);
        return matricula;
    }

    public Optional<Matricula> findByNumeroEstudante(String numeroEstudante) {
        Optional<Matricula> matricula = matriculaRepository.findByNumeroEstudante(numeroEstudante);
        return matricula;
    }

}
