/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Grupo extends GenericDomin {

    @Column(length = 50, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    private String nome;
    @ManyToMany
    @Size(min = 1, message = "Selecione um grupo")
    @JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "codigo_grupo"),
            inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
    private List<Permissao> permissoes;
    @Transient
    private String estadoFormato;
    private boolean estado = true;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
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
