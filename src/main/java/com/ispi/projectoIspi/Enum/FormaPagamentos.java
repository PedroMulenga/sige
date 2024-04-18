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
public enum FormaPagamentos {
    CASSH("CASSH"),
    TPA("TPA"),
    TRANSFERENCIA("TRANSFERENCIA");
    private String listaGrupos;

    private FormaPagamentos(String listaGrupos) {
        this.listaGrupos = listaGrupos;

    }

}
