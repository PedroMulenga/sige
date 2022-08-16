/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Service.MunicipioService;
import com.ispi.projectoIspi.Service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.model.Municipio;
import com.ispi.projectoIspi.model.Provincia;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/cadastrarMunicipio")
    public ModelAndView novoRegisto(Municipio municipio) {
        ModelAndView mv = new ModelAndView("outros/municipio");
        mv.addObject("provincias", provinciaService.findAll());
        return mv;
    }

    @PostMapping("/cadastrarMunicipio")
    public ModelAndView cadastrarMunicipio(@Valid Municipio municipio, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novoRegisto(municipio);
        }
        municipioService.addNew(municipio);
        attribute.addFlashAttribute("success", "Município salvo com sucesso!");
        return new ModelAndView("redirect:/municipios/cadastrarMunicipio");

    }

    @RequestMapping("/listarMunicipios")
    public ModelAndView listarMunicipio(Municipio provincia) {
        return listarMunicipio(1);
    }

    @GetMapping("/listarMunicipios/{pageNumber}")
    public ModelAndView listarMunicipio(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("outros/listaMunicipios");
        Page<Municipio> page = municipioService.findAll(paginaCorrente);
        List<Municipio> listaMunicipio = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaMunicipio", listaMunicipio);
        return mv;
    }

    @GetMapping("/editarMunicipio/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("outros/municipio");
        Optional<Municipio> municipioOptional = municipioService.getOne(codigo);
        mv.addObject("municipio", municipioOptional.get());
        mv.addObject("provincias", provinciaService.findAll());
        return mv;

    }

    @GetMapping(value = "/eliminarMunicipio/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        municipioService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Municipio> buscarPorCodigoProvincia(@RequestParam(name = "provincia", defaultValue = "-1") Long codigoProvincia) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return municipioService.findByProvinciaCodigo(codigoProvincia);
    }

}
