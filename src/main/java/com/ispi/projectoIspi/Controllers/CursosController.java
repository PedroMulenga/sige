/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.model.Cursos;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/cursos")
public class CursosController {

    @Autowired
    private CursosService cursosService;

    @GetMapping("/cadastrarCursos")
    public ModelAndView carregarForm(Cursos cursos) {
        ModelAndView mv = new ModelAndView("outros/curso");
        return mv;
    }

    @PostMapping("/cadastrarCursos")
    public ModelAndView cadastrarCursos(@Valid @ModelAttribute Cursos cursos, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return carregarForm(cursos);
        }
        try {
            cursosService.addNew(cursos);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarForm(cursos);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return new ModelAndView("redirect:/cursos/cadastrarCursos");

    }

    @RequestMapping("/listarCursos")
    public ModelAndView listarCursos() {

        return listarCursos(1);
    }

    @GetMapping("/listarCursos/{pageNumber}")
    public ModelAndView listarCursos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/listaCursos");
        Page<Cursos> page = cursosService.findAll(paginaCorrente);
        List<Cursos> listaDeCursos = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeCursos", listaDeCursos);

        return mv;
    }

    @GetMapping("/editarCurso/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/curso");
        Optional<Cursos> cursoOptional = cursosService.getOne(codigo);
        mv.addObject("cursos", cursoOptional.get());
        return mv;

    }

    @GetMapping(value = "/eliminarCurso/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        cursosService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }
}
