/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Service.GrupoService;
import com.ispi.projectoIspi.Service.PermissaoService;
import com.ispi.projectoIspi.model.Grupo;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;
    @Autowired
    private PermissaoService permissaoService;

    @GetMapping("/cadastroGrupo")
    public ModelAndView novoGrupo(Grupo grupo) {
        ModelAndView mv = new ModelAndView("security/grupo");
        mv.addObject("permissoes", permissaoService.getAll());
        return mv;
    }

    @PostMapping("/cadastroGrupo")
    public ModelAndView cadastroGrupos(@Valid @ModelAttribute Grupo grupo, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novoGrupo(grupo);
        }
        try {
            grupoService.addNew(grupo);
            if (grupo.getCodigo() != null) {
                attribute.addFlashAttribute("success", "Registo actualizado com sucesso!");
            } else {
                attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
            }
        } catch (NomeExistenteException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return novoGrupo(grupo);
        }
        return new ModelAndView("redirect:/grupos/cadastroGrupo");
    }

    @RequestMapping("/listarGrupos")
    public ModelAndView listarGrupos() {

        return listarGrupos(1);
    }

    @GetMapping("/listarGrupos/{pageNumber}")
    public ModelAndView listarGrupos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("security/listaGrupos");
        Page<Grupo> page = grupoService.findAll(paginaCorrente);
        List<Grupo> listaDeGrupo = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeGrupos", listaDeGrupo);

        return mv;
    }

    @GetMapping("/editarGrupo/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/grupo");
        Optional<Grupo> grupoOptional = grupoService.getOne(codigo);
        mv.addObject("grupo", grupoOptional.get());
        mv.addObject("permissoes", permissaoService.getAll());
        return mv;

    }

}
