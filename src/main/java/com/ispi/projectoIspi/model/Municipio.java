/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispi.projectoIspi.validadores.Strings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tecno-base
 */
@SuppressWarnings("serial")
@Entity
public class Municipio extends GenericDomin {

    @Column(length = 50, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    @Strings
    private String nome;
    @NotNull(message = "Selecione a província pertecente")
    @ManyToOne()
    @JoinColumn(nullable = false)
    //@JsonIgnore
    private Provincia provincia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

}
