/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.StatusUsuario;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.UsuarioService;
import com.ispi.projectoIspi.model.Grupo;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Usuario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class CriarContaUsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MatriculaService matriculaService;
    private Matricula matricula = new Matricula();
    private List<Grupo> grupos = new ArrayList<>();
    private Optional<Usuario> usuarioSistema;
    private static String caminhoImagem = "C:/EASYMULL/imagens/usuarios/";

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal User user, Usuario usuario, RedirectAttributes attribute) {
        matricula = new Matricula();
        if (user != null) {
            return new ModelAndView("redirect:/dashboard");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/login")
    public ModelAndView login(@AuthenticationPrincipal User user, Usuario usuario, RedirectAttributes attribute) {
        matricula = new Matricula();
        if (user != null) {
            return new ModelAndView("redirect:/dashboard");
        }
        return new ModelAndView("login");
    }

    //calculando o total de usuários no sistema e renderizar no dashboard
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView mv = new ModelAndView("dashboard");
        Page<Usuario> page = usuarioService.totalUsuario();
        long totalDeUsuarios = page.get().count();
        mv.addObject("totalDeUsuarios", totalDeUsuarios);
        mv.addObject("listaDeUsuarios", usuarioService.getAll());
        return mv;
    }

    //ResponseEntity
    @PutMapping(path = "/usuarios/status", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces ={ MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    public void atualizarEstatus(@RequestBody @RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
        Arrays.asList(codigos).forEach(System.out::println);
        System.out.println("Status »»»»»" + statusUsuario);
        usuarioService.alterarStatus(codigos, statusUsuario);

    }

    @RequestMapping("/usuarios/listarUsuarios")
    public ModelAndView listarAlunos() {
        return listarAlunos(1);
    }

    @GetMapping("/usuarios/listarUsuarios/{pageNumber}")
    public ModelAndView listarAlunos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("security/listaUsuarios");
        Page<Usuario> page = usuarioService.findAll(paginaCorrente);
        List<Usuario> listaDeUsuarios = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeUsuarios", listaDeUsuarios);

        return mv;
    }

    /*@GetMapping("/criarContaUsuarios")
    public ModelAndView novaContaUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("login");
        grupos = grupoRepository.findByNomeIgnoreCase("Estudante");
        matricula = new Matricula();
        return mv;
    }

    @PostMapping("/criarContaUsuarios")
    public ModelAndView criarContaUsuarios(@Valid Usuario usuario, BindingResult result,
            RedirectAttributes attribute) {
        findEstudanteByBi(usuario);
        if (result.hasErrors()) {
            return novaContaUsuario(usuario);
        }
        if (matricula == null) {
            matricula = new Matricula();
            attribute.addFlashAttribute("warning", "Não lhe é permitido criar conta no ISPI Payment, Por favor contacte a secretaria da Instituição");
            return new ModelAndView("redirect:/criarContaUsuarios");
        }
        try {
            usuario.setGrupos(grupos);
            usuarioService.addNew(usuario);
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novaContaUsuario(usuario);
        }
        attribute.addFlashAttribute("success", "Conta ISPI Payment criada com sucesso, Faça o seu Login!");
        return new ModelAndView("redirect:/criarContaUsuarios");
    }*/

  /*  @PostMapping(value = "/verificarUsuario/{bi}{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Matricula findEstudanteByBi(Usuario usuario) {
        matricula = matriculaService.findByBiEstudanteAndSituacao(usuario.getBi(), usuario.getNumeroEstudante());
        return matricula;
    }*/

    @GetMapping("/403")
    public String acessoNegado() {
        return "error/403";
    }

    @GetMapping(value = "/usuarios/eliminarUsuario/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        usuarioService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    //CARREGANDO O PERFIL DO USUÁRIO
    @GetMapping("/listarPerfil")
    public ModelAndView listaUsuarios(@AuthenticationPrincipal User user, Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/perfilUsuario");
        if (user != null) {
            usuarioSistema = usuarioService.findByEmailIgnoreCase(user.getUsername());
        }
        mv.addObject("listaDeUsuarios", usuarioSistema.get());
        return mv;
    }
    //EDITANDO O PERFIL DO USUÁRIO ESPECÍFICO

    @GetMapping("/editarPerfil/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("security/editarPerfil");
        Optional<Usuario> usuario = usuarioService.getOne(codigo);
        mv.addObject("grupos", grupos = usuarioSistema.get().getGrupos());
        mv.addObject("usuario", usuario.get());
        return mv;

    }

    @GetMapping("/atualizarConta")
    public ModelAndView atualizarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("security/editarPerfil");
        return mv;
    }

    @PostMapping("/atualizarConta")
    public ModelAndView atualizarContaUsuarios(@Valid Usuario usuario, BindingResult result,
            RedirectAttributes attribute, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            return atualizarUsuario(usuario);
        }
        try {
            attribute.addFlashAttribute("success", "Seus dados foram Actualizados com sucesso!");
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                Path caminho = Paths.get(caminhoImagem + String.valueOf(usuario.getFuncionario()) + multipartFile.getOriginalFilename());
                Files.write(caminho, bytes);
                usuario.setNomeImagen(String.valueOf(usuario.getFuncionario()) + multipartFile.getOriginalFilename());
            }
            usuario.setGrupos(grupos);
            usuarioService.addNew(usuario);

        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (SenhaObrigatoriaNovoUsuario e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return atualizarUsuario(usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/listarPerfil");
    }

    @GetMapping("/mostrarImagemUsuario/{imagem}")
    @ResponseBody
    public byte[] carregarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File("C:/EASYMULL/imagens/usuarios/" + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }
}
