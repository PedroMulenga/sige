/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.model.AnoLectivo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface AnoLectivoRepository extends JpaRepository<AnoLectivo, Long> {

    Optional<AnoLectivo> findByNomeIgnoreCaseAndEstadoIsTrue(String nome);

    List<AnoLectivo> findByEstadoIsTrue();

    @Query("SELECT a FROM AnoLectivo a Where a.estado=true")
    AnoLectivo findAnoLectivoEmCurso();

}
