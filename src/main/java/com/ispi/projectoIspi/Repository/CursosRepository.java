/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;



import com.ispi.projectoIspi.model.Cursos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface CursosRepository extends JpaRepository<Cursos, Long>{
    Optional<Cursos> findByNomeIgnoreCase(String nome);
    
}
