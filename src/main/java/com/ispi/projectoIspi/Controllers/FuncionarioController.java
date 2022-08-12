/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.Genero;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.DataInvalidaException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Service.FuncionarioService;
import com.ispi.projectoIspi.Service.MunicipioService;
import com.ispi.projectoIspi.Service.ProvinciaService;
import com.ispi.projectoIspi.model.Funcionario;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private MunicipioService municipioService;

    @GetMapping("/cadastrarFuncionarios")
    public ModelAndView novoFuncionario(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("funcionarios/funcionario");
        mv.addObject("provincias", provinciaService.findAll());
        return mv;
    }

    @PostMapping("/cadastrarFuncionarios")
    public ModelAndView cadastroFuncionarios(@Valid @ModelAttribute Funcionario funcionario, BindingResult result, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("redirect:/cadastrarFuncionarios");
        if (result.hasErrors()) {
            return novoFuncionario(funcionario);
        }
        try {
            funcionarioService.addNew(funcionario);
            if (funcionario.getCodigo() != null) {
                attribute.addFlashAttribute("success", "Registo actualizado com sucesso!");
            } else {
                attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
            }
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        } catch (DataInvalidaException e) {
            result.rejectValue("dataNascimento", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        } catch (NomeExistenteException e) {
            result.rejectValue("numCRM", e.getMessage(), e.getMessage());
            return novoFuncionario(funcionario);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return mv;
    }

    @RequestMapping("/listarFuncionarios")
    public ModelAndView listarFuncionarios() {

        return listarFuncionarios(1);
    }

    @GetMapping("/listarFuncionarios/{pageNumber}")
    public ModelAndView listarFuncionarios(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("funcionarios/listaFuncionarios");
        Page<Funcionario> page = funcionarioService.findAll(paginaCorrente);
        List<Funcionario> listaDeFuncionarios = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaFuncionarios", listaDeFuncionarios);
        return mv;
    }

    @GetMapping("/editarFuncionario/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("funcionarios/funcionario");
        Optional<Funcionario> funcionarioOptional = funcionarioService.getOne(codigo);
        mv.addObject("funcionario", funcionarioOptional.get());
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("genero", Genero.values());
        return mv;

    }

    @GetMapping(value = "/eliminarFuncionario/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        funcionarioService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

}
