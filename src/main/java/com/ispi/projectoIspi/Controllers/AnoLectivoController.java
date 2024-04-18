/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.AnoLectivoService;
import com.ispi.projectoIspi.Service.DisciplinaService;
import com.ispi.projectoIspi.model.AnoLectivo;
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
@RequestMapping("/outros")
public class AnoLectivoController {

    @Autowired
    private AnoLectivoService anoLectivoService;

    @GetMapping("/cadastrarAnoLectivo")
    public ModelAndView carregarFormAnoLectivo(AnoLectivo anoLectivo) {
        ModelAndView mv = new ModelAndView("outros/anoLectivo");
        return mv;
    }

    @PostMapping("/cadastrarAnoLectivo")
    public ModelAndView cadastrarAnoLectivo(@Valid @ModelAttribute AnoLectivo anoLectivo, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return carregarFormAnoLectivo(anoLectivo);
        }
        try {
            anoLectivoService.addNew(anoLectivo);
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return carregarFormAnoLectivo(anoLectivo);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return new ModelAndView("redirect:/outros/cadastrarAnoLectivo");

    }

    @RequestMapping("/listarAnoLectivos")
    public ModelAndView listarAnoLectivos() {

        return listarAnoLectivos(1);
    }

    @GetMapping("/listarAnoLectivos/{pageNumber}")
    public ModelAndView listarAnoLectivos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/listaAnoLectivo");
        Page<AnoLectivo> page = anoLectivoService.findAll(paginaCorrente);
        List<AnoLectivo> listaDeAnoLectivos = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeAnoLectivos", listaDeAnoLectivos);

        return mv;
    }

    @GetMapping("/editarAnoLectivo/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/anoLectivo");
        Optional<AnoLectivo> anoLectivoOptional = anoLectivoService.getOne(codigo);
        mv.addObject("anoLectivo", anoLectivoOptional.get());
        return mv;

    }

    @GetMapping(value = "/eliminarAnoLectivo/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        anoLectivoService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }
}
