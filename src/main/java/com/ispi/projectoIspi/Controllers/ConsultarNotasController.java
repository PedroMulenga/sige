/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.NotaAlunoService;
import com.ispi.projectoIspi.model.Emolumento;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.NotaAluno;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/notas")
public class ConsultarNotasController {

    @Autowired
    private NotaAlunoService notaAlunoService;
    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Iterable<Matricula> matriculas;
    Matricula matricula;
    List<NotaAluno> notasAluno = new ArrayList<>();

    @RequestMapping(value = "/listar_notas", method = RequestMethod.GET)
    public ModelAndView carregarPagina() {
        ModelAndView mv = new ModelAndView("alunos/consultar_notas");
        //mv.addObject("servicos", servicoService.findByEstadoIsTrue());
        return mv;
    }

    @PostMapping(value = "/consultar_nota/{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Matricula findByNumeroEstudante(@PathVariable("numeroEstudante") Long numEstudante) {
        matricula = matriculaService.findByNumeroEstudante(numEstudante);
        if (matricula != null) {
            notasAluno = notaAlunoService.findByMatricula(matricula);
        } else {
            notasAluno = new ArrayList<>();
        }
        return matricula;
    }

    @RequestMapping(value = "/carregar_notas", method = RequestMethod.GET)
    public ModelAndView carregarPaginaFragmentoFotoMatricula() {
        ModelAndView mv = new ModelAndView("alunos/fragmento_notas");
        mv.addObject("litaDeNotas", notasAluno);
        return mv;
    }

    @GetMapping("/gerar_boletim_notas")
    public void gerarBoletim(Emolumento emolumento, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGO", matricula.getCodigo());
        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/templates/relatorios/boletim_notas.jasper"), parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; factura_recibo.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);

    }
    @GetMapping("/gerar_caderneta_aluno")
    public void gerarCaderneta(Emolumento emolumento, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGO", matricula.getCodigo());
        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/templates/relatorios/caderneta_aluno.jasper"), parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; factura_recibo.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);

    }

}
