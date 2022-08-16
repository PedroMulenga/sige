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
import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.NumeroEstudanteUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.TelefoneUsuarioExistenteException;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.ProvinciaService;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.dto.TotalMatricula;
import com.ispi.projectoIspi.helper.MatriculasImpl;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.io.File;
import java.io.FileInputStream;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
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
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private TurmaService turmasService;
    @Autowired
    private MatriculasImpl matriculasImpl;
    @Autowired
    private ProvinciaService provinciaService;
    private static String caminhoImagem = "C:/EASYMULL/imagens/";
    private Turma turmas = new Turma();
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/matriculaAluno")
    public ModelAndView novaMatricula(Matricula matricula) {
        ModelAndView mv = new ModelAndView("alunos/matricula");
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("turma", turmas);
        return mv;
    }

    @PostMapping("/matriculaAluno")
    public ModelAndView matriculaAluno(@Valid @ModelAttribute Matricula matricula,
            BindingResult result, RedirectAttributes attribute, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            return novaMatricula(matricula);
        }
        if (turmas != null) {
            matricula.setTurma(turmas);

        }
        try {
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                Path caminho = Paths.get(caminhoImagem + String.valueOf(matricula.getNumeroEstudante()) + multipartFile.getOriginalFilename());
                Files.write(caminho, bytes);
                matricula.setNomeImagen(String.valueOf(matricula.getNumeroEstudante()) + multipartFile.getOriginalFilename());
            }
            matricula.setTurma(turmas);
            matriculaService.addNew(matricula);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmailUsuarioExistenteException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (BiUsuarioExistenteException e) {
            result.rejectValue("bi", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (TelefoneUsuarioExistenteException e) {
            result.rejectValue("telefone", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (DataInvalidaException e) {
            result.rejectValue("dataNascimento", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (NomeExistenteException e) {
            result.rejectValue("numCRM", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (NumeroEstudanteUsuarioExistenteException e) {
            result.rejectValue("numeroEstudante", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        } catch (EntidadeUsuarioExistenteException e) {
            result.rejectValue("numeroEstudante", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        }
        attribute.addFlashAttribute("success", "Matrícula feita com sucesso!");
        return new ModelAndView("redirect:/matriculas/matriculaAluno");
    }

    @GetMapping("/editarAlunoMatriculado/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("alunos/matricula");
        Optional<Matricula> matriculaOptional = matriculaService.getOne(codigo);
        mv.addObject("matricula", matriculaOptional.get());
        mv.addObject("provincias", provinciaService.findAll());
        mv.addObject("turma", turmas);
        mv.addObject("genero", Genero.values());
        return mv;

    }

    @GetMapping("/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] carregarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File("C:/EASYMULL/imagens/" + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }

    @GetMapping(value = "/eliminarAlunoMatricula/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        matriculaService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    @GetMapping(value = "/totalEstudantelPorAno")
    @ResponseBody
    public List<TotalMatricula> buscarTotalPorAno() {
        return matriculasImpl.totalMatricula();
    }

    @GetMapping("/{codigo}")
    public ModelAndView detalhesTurmas(@PathVariable("codigo") Long codigo) {
        turmas = turmasService.getOne(codigo);
        ModelAndView mv = new ModelAndView("redirect:/matriculas/listarMatriculas");
        // mv.addObject("matricula", new Matricula());
        return mv;
    }

    @RequestMapping("/listarMatriculas")
    public ModelAndView listarMatriculas() {

        return listarMatriculas(1);
    }

    @GetMapping("/listarMatriculas/{pageNumber}")
    public ModelAndView listarMatriculas(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("alunos/detalhesTurmaAlunosMatriculados");
        Page<Matricula> page = matriculaService.findByTurma(turmas, paginaCorrente);
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("turma", turmas);
        mv.addObject("listaMatriculasAlunos", page.getContent());

        return mv;
    }

    @GetMapping("/gerar_ficha_pdf/{codigo}")
    public void gerarFichaAluno(@PathVariable("codigo") Long codigo, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        JasperReport inputStream = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/templates/relatorios/matricula_ficha_alino_especifico.jrxml"));
        Optional<Matricula> matriculaOptional = matriculaService.getOne(codigo);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGO", matriculaOptional.get().getCodigo());
        JasperPrint print = JasperFillManager.fillReport(inputStream, parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; ficha_alunos.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);

    }

    @GetMapping("/relatorio_lista_alunos")
    public ModelAndView relatorioListaAlunos(Matricula matricula) {
        ModelAndView mv = new ModelAndView("report_pages/report_lista_alunos");
        mv.addObject("turmas", turmasService.getAll());
        return mv;
    }

    @PostMapping("/relatorio_lista_alunos")
    public void imprimirListaAlunos(@RequestParam("turma") Turma turma, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, InterruptedException {
        List<Matricula> matriculaOptional = matriculaService.findByTurma(turma);
        try {
            Connection conexao = jdbcTemplate.getDataSource().getConnection();
            JasperReport inputStream = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/templates/relatorios/lista_alunos_turma.jrxml"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("CODIGOTURMA", turma.getCodigo());
            JasperPrint print = JasperFillManager.fillReport(inputStream, parametros, conexao);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; lista_alunos.pdf");
            OutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(print, stream);
        } catch (JRException error) {
            error.printStackTrace();
        }
    }
}
