/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.GrupoRepository;
import com.ispi.projectoIspi.model.Grupo;
import com.ispi.projectoIspi.model.Permissao;
import com.ispi.projectoIspi.model.Provincia;
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
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> getAll() {
        return (List<Grupo>) grupoRepository.findAll();
    }

    public Page<Grupo> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return grupoRepository.findAll(pageable);
    }

    public Optional<Grupo> getOne(Long codigo) {
        return grupoRepository.findById(codigo);

    }

    public void addNew(Grupo grupo) {
        Optional<Grupo> grupoOptional = grupoRepository.findByNome(grupo.getNome());
        if (grupoOptional.isPresent() && !grupoOptional.get().equals(grupo)) {
            throw new NomeExistenteException("Nome já cadastrado!");
        }
        grupoRepository.save(grupo);
    }

    public void update(Grupo grupo) {
        grupoRepository.save(grupo);

    }

    public void delete(Long codigo) {
        grupoRepository.deleteById(codigo);

    }

}
