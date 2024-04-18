/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.model.AnoLectivo;
import com.ispi.projectoIspi.model.Disciplina;
import com.ispi.projectoIspi.model.Funcionario;
import com.ispi.projectoIspi.model.ProfessorTurma;
import com.ispi.projectoIspi.model.Turma;
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
public interface ProfessorTurmaRepository extends JpaRepository<ProfessorTurma, Long> {

    public Optional<ProfessorTurma> findByFuncionarioAndTurmaAndDisciplinaAndAnoLectivo(Funcionario funcionario, Turma turma, Disciplina disciplina, AnoLectivo anoLectivo);

    @Query("SELECT p FROM ProfessorTurma p JOIN p.funcionario f JOIN p.anoLectivo a WHERE p.funcionario= :funcionario AND a.estado=true")
    public Page<ProfessorTurma> findByFuncionarioAndAnoLectivo(@Param("funcionario") Funcionario funcionario, Pageable pageable);

    Page<ProfessorTurma> findByTurma(Turma turma, Pageable pageable);
    /*@Query("SELECT p FROM ProfessorTurma p JOIN p.funcionario f WHERE f.email= :email")
    public Page<ProfessorTurma> findByEmailFuncionario(@Param("email")String email,Pageable pageable);
    
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

    public Optional<Matricula> findByTelefone(Integer telefone);*/

}
