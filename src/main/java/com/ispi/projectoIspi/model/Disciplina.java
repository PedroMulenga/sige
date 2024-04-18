/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.validadores.Strings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class Disciplina extends GenericDomin {

    @Strings
    @NotBlank(message = "O campo nome é obrigatório!")
    @Column(length = 50, nullable = false)
    private String nome;
    @Transient
    private String estadoFormato;
    private boolean estado = true;

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
}
