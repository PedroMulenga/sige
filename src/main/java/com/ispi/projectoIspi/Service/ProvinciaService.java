/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Repository.ProvinciaRepository;
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
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Page<Provincia> getAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return provinciaRepository.findAll(pageable);
    }
    public List<Provincia> findAll() {
        return (List<Provincia>) provinciaRepository.findAll();
    }

    public Optional<Provincia> getOne(Long codigo) {
        return provinciaRepository.findById(codigo);

    }

    public void addNew(Provincia provincia) {
        provinciaRepository.save(provincia);
    }

    public void update(Provincia provincia) {
        provinciaRepository.save(provincia);

    }
    public void delete(Long codigo){
        provinciaRepository.deleteById(codigo);
        
    }

}
