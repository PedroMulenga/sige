/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.model.Disciplina;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.NotaAluno;
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
public interface NotaAlunoRepository extends JpaRepository<NotaAluno, Long> {

    Optional<NotaAluno> findByMatriculaAndDisciplina(Matricula matricula, Disciplina disciplina);

    @Query("SELECT n FROM NotaAluno n JOIN n.matricula m JOIN m.anoLectivo a WHERE m.turma=:turma AND n.disciplina=:disciplina AND a.estado=true")
    Page<NotaAluno> findByNotasAndTurmaAndDisciplina(@Param("turma") Turma turma, @Param("disciplina") Disciplina disciplina, Pageable pageable);

    List<NotaAluno> findByMatricula(Matricula matricula);

}
