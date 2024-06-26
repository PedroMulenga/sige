/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.converter;

import java.math.BigDecimal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author PEDRO P MULENGA
 */
@Component
public class StringToDouble implements Converter<String, Double> {

    @Override
    public Double convert(String source) {
       source = source.trim();
       if(source.length()>0){
           source = source.replace(".", "").replace(",", ".");
           return Double.parseDouble(source);
       }
       return 0.;
    }

}
