/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.model.Aluno;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    //public Aluno findByCodigo(Long codigo);

    public Aluno findByBiIgnoreCase(String bi);

    //public Aluno findByBi(String bi);

    public Optional<Aluno> findByEmailIgnoreCase(String email);

    public Optional<Aluno> findByTelefone(Integer telefone);

}
