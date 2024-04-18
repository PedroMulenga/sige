/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class PagesControllers {

    @GetMapping("/paginas/servicePage")
    public ModelAndView sirvicePage() {
        ModelAndView mv = new ModelAndView("servicos/service");
        return mv;
    }
    @GetMapping("/report/menu_reports")
    public ModelAndView reportPags() {
        ModelAndView mv = new ModelAndView("report_pages/report_menu");
        return mv;
    }

    @GetMapping("/paginas/other")
    public ModelAndView otherPage() {
        ModelAndView mv = new ModelAndView("outros/other");
        return mv;
    }
    @GetMapping("/paginas/secretariaPage")
    public ModelAndView secretariaPage() {
        ModelAndView mv = new ModelAndView("secretaria/secretaria");
        return mv;
    }
    @GetMapping("/transportes/transportePage")
    public ModelAndView transportePage() {
        ModelAndView mv = new ModelAndView("servicos/transportes");
        return mv;
    }
    @GetMapping("/myPayments")
    public ModelAndView myPayments() {
        ModelAndView mv = new ModelAndView("servicos/meusPagamentos");
        return mv;
    }
    @GetMapping("/paginas/security")
    public ModelAndView security() {
        ModelAndView mv = new ModelAndView("security/menuUser");
        return mv;
    }
    @GetMapping("/paginas/menu_estudantes")
    public ModelAndView estudantes() {
        ModelAndView mv = new ModelAndView("secretaria/menu_estudante");
        return mv;
    }

}
