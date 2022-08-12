/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.Service.ProvinciaService;
import com.ispi.projectoIspi.model.Provincia;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/cadastrarProvincia")
    public ModelAndView carregarForm(Provincia provincia) {
        ModelAndView mv = new ModelAndView("outros/provincia");
        mv.addObject("provincia", new Provincia());
        return mv;
    }

    @RequestMapping("/listarProvincias")
    public ModelAndView listarProvincias(Provincia provincia) {
        return listByPage(1);
    }

    @GetMapping("/page/{pageNumber}")
    public ModelAndView listByPage(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/provincia");
        Page<Provincia> page = provinciaService.getAll(paginaCorrente);
        List<Provincia> listaDeProvincia = page.getContent();
        //paginaCorrente= 1;
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        //int numeroPagina = page.getNumber();
        //int primeiroRegisto= paginaActual * totalRegistoPorPaginas;
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("provincia", new Provincia());
        //mv.addObject("totalRegistoPorPaginas", totalRegistoPorPaginas);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaProvincia", listaDeProvincia);
        return mv;
    }

    @PostMapping("/cadastrarProvincia")
    public ModelAndView cadastrarProvincia(@Valid Provincia provincia, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return listByPage(1);
        }
        provinciaService.addNew(provincia);
        return new ModelAndView("redirect:/listarProvincias");

    }

    @RequestMapping("/getOne")
    @ResponseBody
    public Optional<Provincia> getOne(Long codigo) {
        return provinciaService.getOne(codigo);
    }

    @RequestMapping(value = "/editarProvincia", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Provincia provincia) {
        provinciaService.update(provincia);
        return "redirect:/listarProvincias";

    }

    @RequestMapping(value = "/eliminarProvincia", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        provinciaService.delete(codigo);
        return "redirect:/listarProvincias";

    }

}
