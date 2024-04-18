/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.CursosService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Servico;
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
public class ServicosController {

    @Autowired
    private ServicoService servicosService;
    //private Turma turmas = new Turma();

    @GetMapping("/cadastrarServico")
    public ModelAndView carregarForm(Servico servico) {
        ModelAndView mv = new ModelAndView("outros/servico");
        mv.addObject("servicos", servicosService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarServico")
    public ModelAndView cadastrarServico(@Valid @ModelAttribute Servico servico, BindingResult result, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("redirect:/cadastrarServico");
        if (result.hasErrors()) {
            return carregarForm(servico);
        }
        try {
            servicosService.addNew(servico);
        } catch (NomeExistenteException e) {
            result.rejectValue("nomeServico", e.getMessage(), e.getMessage());
            return carregarForm(servico);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return mv;

    }

    @RequestMapping("/listarServicos")
    public ModelAndView listarServicos() {
        return listarServicos(1);
    }

    @GetMapping("/listarServicos/{pageNumber}")
    public ModelAndView listarServicos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/listaServicos");
        Page<Servico> page = servicosService.findAll(paginaCorrente);
        List<Servico> listaDeServicos = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeServicos", listaDeServicos);
        //mv.addObject("cursos", cursosService.getAll());
        return mv;
    }

    @GetMapping("/editarServico/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/servico");
        Servico servicoOptional = servicosService.getOne(codigo);
        mv.addObject("servico", servicoOptional);
        return mv;

    }

    @GetMapping(value = "/eliminarServico/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        servicosService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

}
