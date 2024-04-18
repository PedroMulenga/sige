/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class ProfessorTurma extends GenericDomin {

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Selecione a Turma!")
    private Turma turma;
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Selecione o Professor!")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Selecione o Ano_Lectivo!")
    private AnoLectivo anoLectivo;
    @NotNull(message = "Selecione a Disciplina!")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Disciplina disciplina;
    @Transient
    private String estadoFormato;
    private boolean estado = true;

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getEstadoFormato() {
        estadoFormato = "Inactivo";
        if (estado == true) {
            estadoFormato = "Activo";
        }
        return estadoFormato;
    }

    public void setEstadoFormato(String estadoFormato) {
        this.estadoFormato = estadoFormato;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public AnoLectivo getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(AnoLectivo anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

}
