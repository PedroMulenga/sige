/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Turma extends GenericDomin {

    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "O campo nome é obrigatório!")
    private String nome;
    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "O campo Classe é obrigatório!")
    private String classe;
    @NotNull(message = "Selecione o curso")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cursos curso;
    @Column(length = 50, nullable = false)
    @NotNull(message = "O campo período é obrigatório!")
    private String periodo;
    @Transient
    private String estadoFormato;
    private boolean estado = true;

    public Turma() {
    }

    public Turma(String nome, String classe, Cursos curso, String periodo, String estadoFormato) {
        this.nome = nome;
        this.classe = classe;
        this.curso = curso;
        this.periodo = periodo;
        this.estadoFormato = estadoFormato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

}
