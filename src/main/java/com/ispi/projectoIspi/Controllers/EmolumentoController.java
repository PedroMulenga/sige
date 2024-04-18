/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.ExceptionMessages.PagamentoUsuarioExistenteException;
import com.ispi.projectoIspi.Service.EmolumentoService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.ServicoService;
import com.ispi.projectoIspi.model.Aluno;
import com.ispi.projectoIspi.model.Emolumento;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.Servico;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
public class EmolumentoController {

    @Autowired
    private EmolumentoService emolumentoService;
    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Matricula matricula;
    Emolumento emolumentoArmazenado;

    @GetMapping("/pagamentoAluno")
    public ModelAndView novoPagamento(Emolumento emolumento) {
        ModelAndView mv = new ModelAndView("servicos/emolumento");
        mv.addObject("servicos", servicoService.findByEstadoIsTrue());
        return mv;
    }

    @PostMapping(value = "/pegarMatricula/{numeroEstudante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Matricula findByNumeroEstudante(@PathVariable("numeroEstudante") Long numEstudante, SituacaoMatricula situacao) {
        matricula = matriculaService.findByNumeroEstudanteAndSituacao(numEstudante, situacao.MATRICULADO);
        return matricula;
    }

    @PostMapping("/pagamentoAluno")
    public ModelAndView salvarPagamento(@Valid @ModelAttribute Emolumento emolumento,
            BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return novoPagamento(emolumento);

        }
        try {
            emolumento.setMatricula(matricula);
            emolumento.setSituacao("PAGO".toUpperCase());
            emolumentoService.addNew(emolumento);
            emolumentoArmazenado = emolumento;
            attribute.addFlashAttribute("success", "Pagamento efectuado com sucesso! Clique em Print para Gerar a Factura");

        } catch (PagamentoUsuarioExistenteException e) {
            result.rejectValue("mesReferente", e.getMessage(), e.getMessage());
            return novoPagamento(emolumento);
        }

        return new ModelAndView("redirect:pagamentoAluno");
    }

    @RequestMapping("/listarPagamentosAlunos")
    public ModelAndView listarPagamentos() {

        return listarPagamentos(1);
    }

    @GetMapping("/listarPagamentosAlunos/{pageNumber}")
    public ModelAndView listarPagamentos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("servicos/listaEmolumento");
        Page<Emolumento> page = emolumentoService.findAll(paginaCorrente);
        List<Emolumento> listaPagamentosAlunos = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaPagamentosAlunos", listaPagamentosAlunos);
        return mv;
    }

    @GetMapping("/editarPagamentoAluno/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("servicos/editarEmolumento");
        Optional<Emolumento> emolumentoOptional = emolumentoService.getOne(codigo);
        mv.addObject("emolumento", emolumentoOptional.get());
        mv.addObject("servicos", servicoService.findByEstadoIsTrue());
        matricula = emolumentoOptional.get().getMatricula();
        emolumentoArmazenado = emolumentoOptional.get();
        return mv;

    }

    @GetMapping(value = "/eliminarPagamentoAluno/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        emolumentoService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    @GetMapping("/gerar_factura")
    public void gerarFactura(Emolumento emolumento, HttpServletResponse response) throws SQLException, FileNotFoundException, IOException, JRException {
        Connection conexao = jdbcTemplate.getDataSource().getConnection();
        //Optional<Emolumento> emolumentoOptional = emolumentoService.getOne(emolumentoArmazenado.getCodigo());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("CODIGO", emolumentoArmazenado.getCodigo());
        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/templates/relatorios/factura_aluno_pagamento.jasper"), parametros, conexao);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; factura_recibo.pdf");
        OutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);

    }

    @PostMapping(value = "/tipoServico/{codigo}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Servico findByTipoServico(@PathVariable("codigo") Long codigo) {
        Servico servico = servicoService.getOne(codigo);
        return servico;
    }
}
