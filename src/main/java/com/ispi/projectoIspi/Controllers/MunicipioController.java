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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        mv.addObject("municipio", new Municipio());
        return mv;
    }

    @GetMapping("/listarMunicipios")
    public ModelAndView listarMunicipio(Municipio municipio, @PageableDefault(size = 5) Pageable pageable) {
        ModelAndView mv = new ModelAndView("outros/municipio");
        Page<Municipio> page = municipioService.findAll(pageable);
        List<Municipio> listaMunicipio = page.getContent();
        long totalRegistoPorPaginas = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        //int numeroPagina = page.getNumber();
        //int primeiroRegisto= paginaActual * totalRegistoPorPaginas;
        //mv.addObject("totalDeItens", totalDeItens);
        //mv.addObject("provincia", new Provincia());
        mv.addObject("totalRegistoPorPaginas", totalRegistoPorPaginas);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaMunicipio", listaMunicipio);
        mv.addObject("provincias", provinciaService.findAll());
        return mv;
    }

    @PostMapping("/cadastrarMunicipio")
    public String cadastrarMunicipio(Municipio municipio) {
        municipioService.addNew(municipio);
        return "redirect:/municipios/listarMunicipios";

    }

    @RequestMapping("/getOneMunicipio")
    @ResponseBody
    public Optional<Municipio> getOne(Long codigo) {
        return municipioService.getOne(codigo);
    }

    @RequestMapping(value = "/editarMunicipio", method = {RequestMethod.PUT, RequestMethod.GET})
    public String editar(Municipio municipio) {
        municipioService.update(municipio);
        return "redirect:/municipios/listarMunicipios";

    }

    @RequestMapping(value = "/eliminarMunicipio", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long codigo) {
        municipioService.delete(codigo);
        return "redirect:/municipios/listarMunicipios";

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
