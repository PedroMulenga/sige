/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.model.Disciplina;
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
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/cadastrarDisciplina")
    public ModelAndView carregarForm(Disciplina disciplina) {
        ModelAndView mv = new ModelAndView("outros/disciplina");
        return mv;
    }

    @PostMapping("/cadastrarDisciplina")
    public ModelAndView cadastrarDisciplina(@Valid @ModelAttribute Disciplina disciplina, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return carregarForm(disciplina);
        }
        try {
            disciplinaService.addNew(disciplina);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarForm(disciplina);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return new ModelAndView("redirect:/cursos/cadastrarDisciplina");

    }

    @RequestMapping("/listarDisciplinas")
    public ModelAndView listarDisciplinas() {

        return listarDisciplinas(1);
    }

    @GetMapping("/listarDisciplinas/{pageNumber}")
    public ModelAndView listarDisciplinas(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/listaDisciplinas");
        Page<Disciplina> page = disciplinaService.findAll(paginaCorrente);
        List<Disciplina> listaDeDisciplinas = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeDisciplinas", listaDeDisciplinas);

        return mv;
    }

    @GetMapping("/editarDisciplina/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/disciplina");
        Optional<Disciplina> disciplinaOptional = disciplinaService.getOne(codigo);
        mv.addObject("disciplina", disciplinaOptional.get());
        return mv;

    }

    @GetMapping(value = "/eliminarDisciplina/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        disciplinaService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }
}
