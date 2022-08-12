/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.TipoFuncionario;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.ispi.projectoIspi.Repository.GrupoRepository;
import com.ispi.projectoIspi.Service.FuncionarioService;
import com.ispi.projectoIspi.Service.UsuarioService;
import com.ispi.projectoIspi.helper.UsuariosImpl;
import com.ispi.projectoIspi.model.Funcionario;
import com.ispi.projectoIspi.model.Usuario;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private FuncionarioService funcionarioService;
    private Funcionario funcionario;
    @Autowired
    private UsuariosImpl usuariosQueries;

    @GetMapping("/usuarios/cadastroUsuarios")
    public ModelAndView novoUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/usuario");
        mv.addObject("grupos", grupoRepository.findAll());
        return mv;
    }

    @GetMapping("/usuarios/editarUsuario/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/editarUsuario");
        Usuario usuario = usuariosQueries.buscarComGrupos(codigo);
        mv.addObject("grupos", grupoRepository.findAll());
        mv.addObject("usuario", usuario);
        return mv;

    }

    @PostMapping("/usuarios/cadastroUsuarios")
    public ModelAndView cadastroUsuarios(@Valid Usuario usuario, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novoUsuario(usuario);
        }
        if (usuario.getCodigo() == null) {
            usuario.setFuncionario(funcionario);
        }
        try {
            usuarioService.addNew(usuario);
            attribute.addFlashAttribute("success", "Usuário salvo com sucesso!");
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novoUsuario(usuario);
        }

        return new ModelAndView("redirect:/usuarios/cadastroUsuarios");
    }

    @PostMapping(value = "/usuarios/usuarioFuncionario/{bi}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Funcionario findFuncionarioByBi(@PathVariable("bi") String bi, TipoFuncionario tipoFuncionario) {
        funcionario = funcionarioService.findByBiIgnoreCaseAndEstadoIsTrue(bi, tipoFuncionario.MOTORISTA);
        return funcionario;
    }

}
