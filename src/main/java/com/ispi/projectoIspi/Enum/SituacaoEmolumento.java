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
public enum SituacaoEmolumento {
    PAGO("Pago"),
    EM_CREDITO("Em Credito"),
    NÃO_PAGO("Não Pago");
    private String situacao;
    private SituacaoEmolumento(String situacao){
        this.situacao= situacao;
        
    }
    
}
