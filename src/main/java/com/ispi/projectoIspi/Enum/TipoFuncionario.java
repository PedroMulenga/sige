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
public enum TipoFuncionario {
    SECRETARIA("Secretaria"),
    PROFESSOR("Professor"),
    MOTORISTA("Motorista");
    private String tipoFuncionario;
    private TipoFuncionario(String tipoFuncionario){
        this.tipoFuncionario= tipoFuncionario;
        
    }
    
}
