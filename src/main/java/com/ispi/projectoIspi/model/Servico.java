/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author justino
 */
@SuppressWarnings("serial")
@Entity
public class Servico extends GenericDomin {

    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "O campo Nome do Serviço  é obrigatório!")
    private String nomeServico;
    @Column(nullable = false)
    @NotNull(message = "O Custo do Serviço a pagar é obrigatório!")
    private Double custoServico;
    @Transient
    private String estadoFormato;
    private boolean estado;

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public Double getCustoServico() {
        return custoServico;
    }

    public void setCustoServico(Double custoServico) {
        this.custoServico = custoServico;
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
