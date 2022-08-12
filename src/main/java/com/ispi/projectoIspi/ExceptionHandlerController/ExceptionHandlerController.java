/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.ExceptionHandlerController;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ModelAndView paginaNaoEncntrada() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/404");

        return modelAndView;
    }

}
