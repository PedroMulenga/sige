/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.ServicosRepository;
import com.ispi.projectoIspi.model.Servico;
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
public class ServicoService {

    @Autowired
    private ServicosRepository servicosRepository;

    public List<Servico> getAll() {
        return (List<Servico>) servicosRepository.findAll();
    }

    public Page<Servico> findAll(int pageNumber) {
        Sort sort = Sort.by("nomeServico").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return servicosRepository.findAll(pageable);
    }

    public Servico getOne(Long codigo) {
        return servicosRepository.findById(codigo).orElse(null);

    }

    public void addNew(Servico servico) {
        Optional<Servico> nomeServico = servicosRepository.findByNomeServicoIgnoreCase(servico.getNomeServico());
        if (nomeServico.isPresent() && !nomeServico.get().equals(servico)) {
            throw new NomeExistenteException("O Serviço Informado já se encontra cadastrada");
        }
        servicosRepository.save(servico);
    }

    public void update(Servico servico) {
        servicosRepository.save(servico);

    }

    public void delete(Long codigo) {
        servicosRepository.deleteById(codigo);

    }
  public List<Servico> findByEstadoIsTrue() {
        return (List<Servico>) servicosRepository.findByEstadoIsTrue();
    }
}
