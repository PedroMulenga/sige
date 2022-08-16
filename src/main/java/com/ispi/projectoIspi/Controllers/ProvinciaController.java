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
@RequestMapping("/provincias")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/cadastrarProvincia")
    public ModelAndView carregarForm(Provincia provincia) {
        ModelAndView mv = new ModelAndView("outros/provincia");
        return mv;
    }

    @PostMapping("/cadastrarProvincia")
    public ModelAndView cadastrarProvincia(@Valid Provincia provincia, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return carregarForm(provincia);
        }
        provinciaService.addNew(provincia);
        attribute.addFlashAttribute("success", "Província salva com sucesso!");
        return new ModelAndView("redirect:/provincias/cadastrarProvincia");

    }

    @RequestMapping("/listarProvincias")
    public ModelAndView listarProvincias(Provincia provincia) {
        return listByPage(1);
    }

    @GetMapping("/listarProvincias/{pageNumber}")
    public ModelAndView listByPage(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/listaProvincias");
        Page<Provincia> page = provinciaService.getAll(paginaCorrente);
        List<Provincia> listaDeProvincia = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaProvincia", listaDeProvincia);
        return mv;
    }

    @GetMapping("/editarProvincia/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/provincia");
        Optional<Provincia> provinciaOptional = provinciaService.getOne(codigo);
        mv.addObject("provincia", provinciaOptional.get());
        return mv;

    }

    @GetMapping(value = "/eliminarProvincia/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        provinciaService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

}
