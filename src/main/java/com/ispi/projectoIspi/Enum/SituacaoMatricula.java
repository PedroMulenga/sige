/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Enum;

/**
 *
 * @author PEDRO P MULENGA
 */
public enum SituacaoMatricula {
    MATRICULADO("Matriculado"),
    TRANCADO("Trancado"),
    CANCELADO("Cancelado");
    private String situacao;
    private SituacaoMatricula(String situacao){
        this.situacao= situacao;
        
    }
    
}
