/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.Genero;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.DataInvalidaException;
import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.AlunoRepository;
import com.ispi.projectoIspi.model.Aluno;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> getAll() {
        return (List<Aluno>) alunoRepository.findAll();
    }

    public Page<Aluno> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return alunoRepository.findAll(pageable);
    }

    public Optional<Aluno> getOne(Long codigo) {
        return alunoRepository.findById(codigo);

    }

    public Aluno findById(Long codigo) {
        return alunoRepository.getOne(codigo);

    }

    public void addNew(Aluno aluno) {
        // CÁLCULO DA IDADE DO ESTUDANTE UTILIZANDO LOCALDATE
        Calendar calendar = Calendar.getInstance();
        int anoConvertido = calendar.get(Calendar.YEAR);
        Locale local = new Locale("pt", "BR");
        DateFormat dfmt = new SimpleDateFormat("yyyy", local);
        String anoAlunoConvertido = dfmt.format(aluno.getDataNascimento());
        int idadeCalculada = anoConvertido - Integer.parseInt(anoAlunoConvertido);
        //CONSULTAS REALIZADAS
        Optional<Aluno> telefoneOptional = alunoRepository.findByTelefone(aluno.getTelefone());
        Aluno biOptional = alunoRepository.findByBiIgnoreCase(aluno.getBi().toUpperCase());

        if (telefoneOptional.isPresent() && !telefoneOptional.get().equals(aluno)) {
            throw new TelefoneUsuarioExistenteException("Este Telefone já se encontra registado!");
        }
        if (biOptional != null && !biOptional.equals(aluno)) {
            throw new BiUsuarioExistenteException("Este B.I já se encontra registado!");
        }
        if (idadeCalculada <= 0) {
            throw new DataInvalidaException("A Data informada é inválida");
        }
        if (!aluno.getNumCRM().equals("") && aluno.getGenero() == Genero.FEMENINO) {
            throw new NomeExistenteException("Este campo não pode ser preenchido para Mulheres");
        }

        if (biOptional != null && !biOptional.equals(aluno)) {
            throw new EntidadeUsuarioExistenteException("O Aluno informado já se encontra Registado!");
        }
        alunoRepository.save(aluno);
        System.out.println("Idae==" + idadeCalculada);
    }

    public void delete(Long codigo) {
        alunoRepository.deleteById(codigo);

    }

    public Aluno findByBi(String bi) {
        return alunoRepository.findByBiIgnoreCase(bi);

    }
}
