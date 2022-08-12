/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.converter;


import com.ispi.projectoIspi.model.Municipio;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author PEDRO P MULENGA
 */
@Component
public class MunicipioConverter implements Converter<String, Municipio> {

    @Override
    public Municipio convert(String codigo) {
       if(!StringUtils.isEmpty(codigo)){
           Municipio municipio = new Municipio();
           municipio.setCodigo(Long.valueOf(codigo));
           return municipio;
       }
       return null;
    }

}
