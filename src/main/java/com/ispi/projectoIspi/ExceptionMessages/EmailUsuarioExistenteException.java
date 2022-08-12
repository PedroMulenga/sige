/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.ExceptionMessages;

/**
 *
 * @author PEDRO P MULENGA
 */
public class EmailUsuarioExistenteException extends RuntimeException {

    public EmailUsuarioExistenteException(String message) {
        super(message);
    }
    
}
