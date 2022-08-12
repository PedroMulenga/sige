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
public enum TipoEmolumento {
    PROPINA("Propina"),
    TRANSPORTE("Transporte"),
    OUTROS_PAGAMENTOS("Outros Pagamentos");
    private String tipoEmolumento;

    private TipoEmolumento(String tipoEmolumento) {
        this.tipoEmolumento = tipoEmolumento;

    }

}
