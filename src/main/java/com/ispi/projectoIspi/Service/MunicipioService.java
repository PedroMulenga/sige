/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Repository.MunicipioRepository;
import com.ispi.projectoIspi.model.Municipio;
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
public class MunicipioService {

    @Autowired
    private MunicipioRepository municipioRepository;

    public List<Municipio> getAll() {
        return (List<Municipio>) municipioRepository.findAll();
    }

    public Page<Municipio> findAll(int pageNumber) {
        Sort sort = Sort.by("provincia.nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return municipioRepository.findAll(pageable);
    }

    public Optional<Municipio> getOne(Long codigo) {
        return municipioRepository.findById(codigo);

    }

    public void addNew(Municipio municipio) {
        municipioRepository.save(municipio);
    }

    public void update(Municipio municipio) {
        municipioRepository.save(municipio);

    }

    public void delete(Long codigo) {
        municipioRepository.deleteById(codigo);

    }

    public List<Municipio> findByProvinciaCodigo(Long codigoProvincia) {
        return (List<Municipio>) municipioRepository.findByProvinciaCodigo(codigoProvincia);
    }

}
