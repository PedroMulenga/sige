/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class Emolumento extends GenericDomin {

    @Column(length = 20, nullable = false)
    @NotBlank(message = "O campo mês referente é obrigatório!")
    private String mesReferente;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento = new Date();
    @ManyToOne
    @JoinColumn(name = "codigo_matricula", nullable = false)
    private Matricula matricula;
    @Column(nullable = false, length = 13)
    private String situacao;
    @Column(nullable = false, precision = 7, scale = 2)
    @NotNull(message = "O valor a pagar é obrigatório!")
    private Double valorApagar;

    @NotNull(message = "Selecione o Tipo de Serviço Referente")
    @ManyToOne()
    @JoinColumn(nullable = false)
    private Servico servico;
    @Column(length = 50, nullable = false)
    @NotNull(message = "Informe a Forma de Pagamento!")
    private String formaDePagamento;

    public String getMesReferente() {
        return mesReferente;
    }

    public void setMesReferente(String mesReferente) {
        this.mesReferente = mesReferente;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Double getValorApagar() {
        return valorApagar;
    }

    public void setValorApagar(Double valorApagar) {
        this.valorApagar = valorApagar;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

}
