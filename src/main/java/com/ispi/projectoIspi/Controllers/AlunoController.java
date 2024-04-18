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
import com.ispi.projectoIspi.ExceptionMessages.NumeroEstudanteUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Service.AlunoService;
import com.ispi.projectoIspi.Service.ProvinciaService;
import com.ispi.projectoIspi.dto.TotalMatricula;
import com.ispi.projectoIspi.model.Aluno;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/inscricoes")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private ProvinciaService provinciaService;
    private static String caminhoImagem = "C:/sige/alunos/";

    @GetMapping("/inscricao_Aluno")
    public ModelAndView novaInscricao(Aluno aluno) {
        ModelAndView mv = new ModelAndView("alunos/inscricao");
        mv.addObject("provincias", provinciaService.findAll());
        return mv;
    }

    @PostMapping("/inscricao_Aluno")
    public ModelAndView inscricaoAluno(@Valid @ModelAttribute Aluno aluno,
            BindingResult result, RedirectAttributes attribute, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            return novaInscricao(aluno);
        }
        try {
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                Path caminho = Paths.get(caminhoImagem + String.valueOf(aluno.getCodigo()) + multipartFile.getOriginalFilename());
                Files.write(caminho, bytes);
                aluno.setNomeImagen(String.valueOf(aluno.getCodigo()) + multipartFile.getOriginalFilename());
            }
            alunoService.addNew(aluno);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novaInscricao(aluno);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return novaInscricao(aluno);
        } catch (DataInvalidaException e) {
            result.rejectValue("dataNascimento", e.getMessage(), e.getMessage());
            return novaInscricao(aluno);
        } catch (NomeExistenteException e) {
            result.rejectValue("numCRM", e.getMessage(), e.getMessage());
            return novaInscricao(aluno);
        }
        attribute.addFlashAttribute("success", "Inscrição feita com sucesso!");
        return new ModelAndView("redirect:/inscricoes/inscricao_Aluno");
    }

    @GetMapping("/editarAluno/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("alunos/inscricao");
        Optional<Aluno> alunoOptional = alunoService.getOne(codigo);
        mv.addObject("aluno", alunoOptional.get());
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("genero", Genero.values());
        return mv;

    }

    @GetMapping("/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] carregarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File("C:/sige/alunos/" + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }

    @GetMapping(value = "/eliminarAluno/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        alunoService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    @RequestMapping("/listar_inscricoes")
    public ModelAndView listarAlunos() {

        return listarAlunos(1);
    }

    @GetMapping("/listar_inscricoes/{pageNumber}")
    public ModelAndView listarAlunos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("alunos/listaInscricoes");
        Page<Aluno> page = alunoService.findAll(paginaCorrente);
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaAlunos", page.getContent());

        return mv;
    }

}
