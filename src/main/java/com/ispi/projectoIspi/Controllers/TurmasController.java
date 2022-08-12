/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.CursosService;
import com.ispi.projectoIspi.Service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.util.List;
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
public class TurmasController {

    @Autowired
    private TurmaService turmasService;
    @Autowired
    private CursosService cursosService;
    private Turma turmas = new Turma();

    @GetMapping("/cadastrarTurma")
    public ModelAndView carregarForm(Turma turma) {
        ModelAndView mv = new ModelAndView("outros/turmas");
        mv.addObject("cursos", cursosService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarTurma")
    public ModelAndView cadastrarTurma(@Valid @ModelAttribute Turma turma, BindingResult result, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("redirect:/cadastrarTurma");
        if (result.hasErrors()) {
            return carregarForm(turma);
        }
        try {
            turmasService.addNew(turma);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarForm(turma);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return mv;

    }

    @RequestMapping("/listarTurmas")
    public ModelAndView listarTurmas() {
        return listarTurmas(1);
    }

    @GetMapping("/listarTurmas/{pageNumber}")
    public ModelAndView listarTurmas(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("alunos/listaTurmasAlunos");
        Page<Turma> page = turmasService.findAll(paginaCorrente);
        List<Turma> listaDeTurmas = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeTurmas", listaDeTurmas);
        //mv.addObject("cursos", cursosService.getAll());
        return mv;
    }

    @GetMapping("/editarTurma/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/turmas");
        Turma turmaOptional = turmasService.getOne(codigo);
        mv.addObject("turma", turmaOptional);
        mv.addObject("cursos", cursosService.getAll());
        return mv;

    }

    @GetMapping(value = "/eliminarTurma/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        turmasService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

}
