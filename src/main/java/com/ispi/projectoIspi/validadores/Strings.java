/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 *
 * @author TECNO BASE
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={})
@Pattern(regexp = "[A-z/á/ã/â/ô/õ/ó/í/é/ê/ú/à/ /Á/Ã/Â/Ô/Õ/Ó/Í/É/Ê/Ú/À/Ç/ç/]+$", message = "Este campo recebe apenas lestras.")
public @interface Strings {
    String message() default "{com.ispi.projectoIspi.validadores.string}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
