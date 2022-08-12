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
public enum MesReferente {
    Janeiro("Janeiro"),
    Fevereiro("Fevereiro"),
    Março("Março"),
    Abril("Abril"),
    Maio("Maio"),
    Junho("Junho"),
    Julho("Julho"),
    Agosto("Agosto"),
    Setembro("Setembro"),
    Outubro("Outubro"),
    Novembro("Novembro"),
    Dezembro("Dezembro");
    private String mesReferente;
    private MesReferente(String mesReferente){
        this.mesReferente= mesReferente;
        
    }
    
}
