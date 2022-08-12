/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.atribute;

import com.ispi.projectoIspi.validadores.AtributoConfirmacaoSenha;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author PEDRO P MULENGA
 */
public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacaoSenha, Object> {

    private String atributo;
    private String atributoConfirmacao;

    @Override
    public void initialize(AtributoConfirmacaoSenha atributoConfirmacaoSenha) {
        this.atributo = atributoConfirmacaoSenha.atributo();
        this.atributoConfirmacao = atributoConfirmacaoSenha.atributoConfirmacao();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valido = false;
        try {
            Object primeiroValor = BeanUtils.getProperty(object, this.atributo);
            Object segundoValor = BeanUtils.getProperty(object, this.atributoConfirmacao);
            valido=todosSaoNull(primeiroValor, segundoValor) || todosSaoIguais(primeiroValor, segundoValor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar o valor dos atributos", e);
        }
        if(!valido){
            context.disableDefaultConstraintViolation();
            String messagem =context.getDefaultConstraintMessageTemplate();
            ConstraintViolationBuilder constraintViolationBuilder = context.buildConstraintViolationWithTemplate(messagem);
            constraintViolationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
        }
        return valido;

    }

    private boolean todosSaoNull(Object primeiroValor, Object segundoValor) {
       return primeiroValor==null && segundoValor==null;
    }

    private boolean todosSaoIguais(Object primeiroValor, Object segundoValor) {
        return primeiroValor!= null && primeiroValor.equals(segundoValor);
    }

}
