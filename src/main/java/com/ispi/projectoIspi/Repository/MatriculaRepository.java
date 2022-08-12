/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Page<Matricula> findByTurma(Turma turma, Pageable pageable);

    List<Matricula> findByTurma(Turma turma);

    public Matricula findByNumeroEstudanteAndSituacao(String numeroEstudante, SituacaoMatricula situacao);

    //public Set<Matricula> findByNumeroEstudante(String numeroEstudante);
    @Query("SELECT m FROM Matricula m  WHERE m.bi= :bi AND m.numeroEstudante= :numeroEstudante AND m.situacao='MATRICULADO'")
    public Matricula findByBiEstudanteAndSituacao(@Param("bi") String bi, @Param("numeroEstudante") String numeroEstudante);

    public Optional<Matricula> findByNumeroEstudante(String numeroEstudante);

    public Iterable<Matricula> findByCodigo(Long codigo);

    public Matricula findByBiIgnoreCase(String bi);

    public Optional<Matricula> findByBi(String bi);

    public Optional<Matricula> findByEmailIgnoreCase(String email);

    public Optional<Matricula> findByTelefone(Integer telefone);

}
