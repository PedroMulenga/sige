/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.helper;

import com.ispi.projectoIspi.dto.TotalMatricula;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author PEDRO P MULENGA
 */
@Configuration
public class MatriculasImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TotalMatricula> totalMatricula() {
        List<TotalMatricula> totalMatriculados = entityManager.createNamedQuery("Matricula.totalEstudantelPorAno").getResultList();
        LocalDate hoje = LocalDate.now();
        for (int i = 1; i <= 5; i++) {
            String anoPego = String.format("%d", hoje.getYear());
            boolean anoEncontrado = totalMatriculados.stream().filter(t -> t.getAno().equals(anoPego)).findAny().isPresent();
            if (!anoEncontrado) {
                totalMatriculados.add(i - 1, new TotalMatricula(anoPego, 0));
            }
            hoje = hoje.minusYears(1);
        }
        return totalMatriculados;
    }
}
