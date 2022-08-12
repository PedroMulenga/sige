/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.validadores;

import com.ispi.projectoIspi.atribute.AtributoConfirmacaoValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 *
 * @author PEDRO P MULENGA
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AtributoConfirmacaoValidator.class })
public @interface AtributoConfirmacaoSenha {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "Senhas diferentes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String atributo();

    String atributoConfirmacao();

}
