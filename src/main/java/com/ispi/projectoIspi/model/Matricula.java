/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class Matricula extends GenericDomin {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Aluno aluno;
    @NotNull(message = "Selecione o Ano_Lectivo!")
    @ManyToOne
    @JoinColumn(nullable = false)
    private AnoLectivo anoLectivo;
    @ManyToOne
    @JoinColumn(name = "turma_codigo", nullable = false)
    private Turma turma;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O Estado da Matrícula é obrigatório!")
    @Column(nullable = false, length = 13)
    private SituacaoMatricula situacao;
    @Temporal(TemporalType.DATE)
    private Date dataRegisto = new Date();

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public AnoLectivo getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public SituacaoMatricula getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoMatricula situacao) {
        this.situacao = situacao;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}
