/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.Genero;
import com.ispi.projectoIspi.Enum.TipoFuncionario;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.DataInvalidaException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.FuncionarioRepository;
import com.ispi.projectoIspi.model.Funcionario;
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
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> getAll() {
        return (List<Funcionario>) funcionarioRepository.findAll();
    }

    public Page<Funcionario> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return funcionarioRepository.findAll(pageable);
    }

    public Optional<Funcionario> getOne(Long codigo) {
        return funcionarioRepository.findById(codigo);

    }

    public void addNew(Funcionario funcionario) {
        // CÁLCULO DA IDADE DO ESTUDANTE UTILIZANDO LOCALDATE
        LocalDate anoSistema = LocalDate.now();
        int anoConvertido = anoSistema.getYear();
        int idadeCalculada = anoConvertido - funcionario.getDataNascimento().getYear();
        // BUSCA DE DADOS
        Optional<Funcionario> emailOptional = funcionarioRepository.findByEmailIgnoreCase(funcionario.getEmail());
        Optional<Funcionario> telefoneOptional = funcionarioRepository.findByTelefone(funcionario.getTelefone());
        Optional<Funcionario> biOptional = funcionarioRepository.findByBiIgnoreCase(funcionario.getBi());
        if (emailOptional.isPresent() && !emailOptional.get().equals(funcionario)) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado!");
        }
        if (telefoneOptional.isPresent() && !telefoneOptional.get().equals(funcionario)) {
            throw new TelefoneUsuarioExistenteException("Este Telefone já se encontra registado!");
        }
        if (biOptional.isPresent() && !biOptional.get().equals(funcionario)) {
            throw new BiUsuarioExistenteException("Este B.I já se encontra registado!");
        }
        if (idadeCalculada <= 14) {
            throw new DataInvalidaException("A Data informada é inválida");
        }
        if (funcionario.getNumCRM() != "" && funcionario.getGenero() == Genero.FEMENINO) {
            throw new NomeExistenteException("Este campo não pode ser preenchido para Mulheres");
        }
        funcionarioRepository.save(funcionario);
    }

    public void delete(Long codigo) {
        funcionarioRepository.deleteById(codigo);

    }

    public Funcionario findByBiIgnoreCaseAndEstadoIsTrue(String Bi, TipoFuncionario tipoFuncionario) {
        Funcionario funcionario = funcionarioRepository.findByBiIgnoreCaseAndTipoFuncionarioAndEstadoIsTrue(Bi, tipoFuncionario.SECRETARIA);
        return funcionario;
    }

}
