/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.filter;

import com.ispi.projectoIspi.model.Emolumento;
import java.util.List;

/**
 *
 * @author PEDRO P MULENGA
 */
public class EmolumentoFilter {

    private String numeroEstudante;
    private String tipoPagamento;
    private List<Emolumento> emolumentos;

    public String getNumeroEstudante() {
        return numeroEstudante;
    }

    public void setNumeroEstudante(String numeroEstudante) {
        this.numeroEstudante = numeroEstudante;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public List<Emolumento> getEmolumentos() {
        return emolumentos;
    }

    public void setEmolumentos(List<Emolumento> emolumentos) {
        this.emolumentos = emolumentos;
    }

}
