/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.validadores.Strings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Provincia extends GenericDomin {

    @Column(length = 50, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    @Strings
    private String nome;
    @Strings
    @NotBlank(message = "O campo sigla é obrigatório!")
    @Size(max = 3, min = 2, message = "O campo deve ter 3 letras no máximo e 2 mínimo")
    @Column(length = 3, nullable = false)
    private String sigla;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
