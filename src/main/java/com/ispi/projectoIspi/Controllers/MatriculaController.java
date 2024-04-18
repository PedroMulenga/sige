/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.EntidadeUsuarioExistenteException;
import com.ispi.projectoIspi.Service.AlunoService;
import com.ispi.projectoIspi.Service.AnoLectivoService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.dto.TotalMatricula;
import com.ispi.projectoIspi.helper.MatriculasImpl;
import com.ispi.projectoIspi.model.Aluno;
import com.ispi.projectoIspi.model.AnoLectivo;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Turma;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.http.MediaType;
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
    private AlunoService alunosService;
    @Autowired
    private MatriculasImpl matriculasImpl;
    @Autowired
    private AnoLectivoService anoLectivoService;
    private Turma turmas;
    private Aluno aluno;
    private Matricula matriculaCapturada = new Matricula();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/matriculaAluno")
    public ModelAndView novaMatricula(Matricula matricula) {
        ModelAndView mv = new ModelAndView("alunos/matricula_aluno");
        mv.addObject("turma", turmas);
        mv.addObject("turmas", turmasService.getAll());
        mv.addObject("anoLectivos", anoLectivoService.findByEstadoIsTrue());
        mv.addObject("matriculaCodigoCapturado", matriculaCapturada.getCodigo());
        return mv;
    }

    @PostMapping("/matriculaAluno")
    public ModelAndView matriculaAluno(@Valid @ModelAttribute Matricula matricula,
            BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novaMatricula(matricula);
        }
        try {

            matricula.setAluno(aluno);
            matricula.setTurma(turmas);
            matriculaService.addNew(matricula);
            matriculaCapturada = matricula;
        } catch (EntidadeUsuarioExistenteException e) {
            result.rejectValue("anoLectivo", e.getMessage(), e.getMessage());
            return novaMatricula(matricula);
        }
        attribute.addFlashAttribute("success", "Matrícula feita com sucesso!");
        return new ModelAndView("redirect:/matriculas/matriculaAluno");
    }

    @PostMapping("/editarAlunoMatriculado/{codigo}")
    public ModelAndView editarMatricula(@Valid @ModelAttribute Matricula matricula,
            BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return editar(matricula.getCodigo());
        }
        try {

            matricula.setAluno(aluno);
            matricula.setTurma(turmas);
            matriculaService.addNew(matricula);
        } catch (EntidadeUsuarioExistenteException e) {
            result.rejectValue("anoLectivo", e.getMessage(), e.getMessage());
            return editar(matricula.getCodigo());
        }
        attribute.addFlashAttribute("success", "Dados da Matrícula atualizados com sucesso!");
        return new ModelAndView("redirect:/matriculas/editarAlunoMatriculado/" + matricula.getCodigo());
    }

    @GetMapping("/editarAlunoMatriculado/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("alunos/editar_matricula_aluno");
        Optional<Matricula> matriculaOptional = matriculaService.getOne(codigo);
        aluno = matriculaOptional.get().getAluno();
        turmas = matriculaOptional.get().getTurma();
        mv.addObject("matricula", matriculaOptional.get());
        mv.addObject("anoLectivos", anoLectivoService.findByEstadoIsTrue());
        mv.addObject("turma", turmas);

        return mv;

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

    @PostMapping(value = "/pegarInscricao/{bi}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Aluno findByBi(@PathVariable("bi") String bi) {
        aluno = alunosService.findByBi(bi);
        return aluno;
    }

    @GetMapping("/gerar_ficha_pdf/{codigo}")
    public void gerarFichaAluno(@PathVariable("codigo") Long codigo, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        Optional<Matricula> matriculaOptional = matriculaService.getOne(codigo);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGO", matriculaOptional.get().getCodigo());
        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/templates/relatorios/ficha_alino_especifico.jasper"), parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; ficha_alunos.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);

    }

    @GetMapping("/relatorio_lista_alunos_pages")
    public ModelAndView relatorioListaAlunos(Matricula matricula) {
        ModelAndView mv = new ModelAndView("report_pages/report_lista_alunos");
        mv.addObject("turmas", turmasService.getAll());
        mv.addObject("anoLectivos", anoLectivoService.findByEstadoIsTrue());
        return mv;
    }

    @GetMapping("/relatorio_lista_alunos")
    public void imprimirListaAlunos(@RequestParam("turma") Turma turma, @RequestParam("anoLectivo") Long anoLectivo, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGOTURMA", turma.getCodigo());
        parametros.put("ANOACADEMICO", anoLectivo);
        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/templates/relatorios/lista_de_alunos_turmas.jasper"), parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; lista_alunos.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
    }

    @GetMapping("/lista_alunos")
    public void imprimirListaNominal(HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        AnoLectivo anoLectivoEmCurso = anoLectivoService.findByAnoLectivoEmCurso();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGOTURMA", turmas.getCodigo());
        parametros.put("ANOACADEMICO", anoLectivoEmCurso.getCodigo());
        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/templates/relatorios/lista_de_alunos_turmas.jasper"), parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; lista_alunos.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
    }
}
