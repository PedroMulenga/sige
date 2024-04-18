/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.AnoLectivoRepository;
import com.ispi.projectoIspi.Repository.CursosRepository;
import com.ispi.projectoIspi.Repository.DisciplinaRepository;
import com.ispi.projectoIspi.model.AnoLectivo;
import com.ispi.projectoIspi.model.Cursos;
import com.ispi.projectoIspi.model.Disciplina;
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
public class AnoLectivoService {

    @Autowired
    private AnoLectivoRepository anoLectivoRepository;

    public List<AnoLectivo> getAll() {
        return (List<AnoLectivo>) anoLectivoRepository.findAll();
    }

    public Page<AnoLectivo> findAll(int pageNumber) {
        Sort sort = Sort.by("nome").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return anoLectivoRepository.findAll(pageable);
    }

    public Optional<AnoLectivo> getOne(Long codigo) {
        return anoLectivoRepository.findById(codigo);

    }

    public void addNew(AnoLectivo anoLectivo) {
        Optional<AnoLectivo> nameAnoLectivo = anoLectivoRepository.findByNomeIgnoreCaseAndEstadoIsTrue(anoLectivo.getNome());
        if (nameAnoLectivo.isPresent() && !nameAnoLectivo.get().equals(anoLectivo)) {
            throw new NomeExistenteException("O Ano Lectivo já se encontra registado!Apenas Um Ano_Lectivo pode estar no Estado Activo");
        }
        anoLectivoRepository.save(anoLectivo);
    }

    public void update(AnoLectivo anoLectivo) {
        anoLectivoRepository.save(anoLectivo);

    }

    public void delete(Long codigo) {
        anoLectivoRepository.deleteById(codigo);

    }

    public List<AnoLectivo> findByEstadoIsTrue() {
        return (List<AnoLectivo>) anoLectivoRepository.findByEstadoIsTrue();
    }

    public AnoLectivo findByAnoLectivoEmCurso() {
        return anoLectivoRepository.findAnoLectivoEmCurso();
    }

}
