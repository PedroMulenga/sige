/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Controllers;

import com.ispi.projectoIspi.ExceptionMessages.NomeExistenteException;
import com.ispi.projectoIspi.Repository.UsuarioRepository;
import com.ispi.projectoIspi.Service.AnoLectivoService;
import com.ispi.projectoIspi.Service.DisciplinaService;
import com.ispi.projectoIspi.Service.FuncionarioService;
import com.ispi.projectoIspi.Service.MatriculaService;
import com.ispi.projectoIspi.Service.NotaAlunoService;
import com.ispi.projectoIspi.Service.ProfessorTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ispi.projectoIspi.Service.TurmaService;
import com.ispi.projectoIspi.model.Matricula;
import com.ispi.projectoIspi.model.NotaAluno;
import com.ispi.projectoIspi.model.ProfessorTurma;
import com.ispi.projectoIspi.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PEDRO P MULENGA
 */
@Controller
@RequestMapping("/funcionarios")
public class ProfessorTurmaController {

    @Autowired
    private ProfessorTurmaService professorTurmaService;
    @Autowired
    private TurmaService turmasService;
    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private NotaAlunoService notaAlunoService;
    @Autowired
    private AnoLectivoService anoLectivoService;
    private ProfessorTurma professorTurma = new ProfessorTurma();

    @GetMapping("/cadastrarProfessorTurma")
    public ModelAndView carregarForm(ProfessorTurma professorTurma) {
        ModelAndView mv = new ModelAndView("funcionarios/professorTurma");
        mv.addObject("turmas", turmasService.getAll());
        mv.addObject("professores", funcionarioService.findByTipoFuncionarioAndEstadoIsTrue());
        mv.addObject("anoLectivos", anoLectivoService.findByEstadoIsTrue());
        mv.addObject("disciplinas", disciplinaService.getAll());
        return mv;
    }

    @PostMapping("/cadastrarProfessorTurma")
    public ModelAndView cadastrarProfessorTurma(@Valid @ModelAttribute ProfessorTurma professorTurma, BindingResult result, RedirectAttributes attribute) {
        ModelAndView mv = new ModelAndView("redirect:/funcionarios/cadastrarProfessorTurma");
        if (result.hasErrors()) {
            return carregarForm(professorTurma);
        }
        try {
            professorTurmaService.addNew(professorTurma);
        } catch (NomeExistenteException e) {
            result.rejectValue("funcionario", e.getMessage(), e.getMessage());
            return carregarForm(professorTurma);
        }
        attribute.addFlashAttribute("success", "Registo salvo com sucesso!");
        return mv;

    }

    @RequestMapping("/listarProfessorTurmas")
    public ModelAndView listarProfessorTurmas() {
        return listarProfessorTurmas(1);
    }

    @GetMapping("/listarProfessorTurmas/{pageNumber}")
    public ModelAndView listarProfessorTurmas(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("funcionarios/listaProfessorTurmas");
        Page<ProfessorTurma> page = professorTurmaService.findAll(paginaCorrente);
        List<ProfessorTurma> listaDeProfessorTurmas = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeProfessorTurmas", listaDeProfessorTurmas);
        return mv;
    }

    @RequestMapping("/listarDisciplinaTurmas")
    public ModelAndView listarDisciplinaTurmas(@AuthenticationPrincipal User user) {
        return listarDisciplinaTurmas(user, 1);
    }

    @GetMapping("/listarDisciplinaTurmas/{pageNumber}")
    public ModelAndView listarDisciplinaTurmas(@AuthenticationPrincipal User user, @PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("notaAlunos/listaDisciplinaTurma");
        Usuario usuario = new Usuario();
        if (user != null) {
            usuario = usuarioRepository.findByEmailIgnoreCase(user.getUsername());
        }
        Page<ProfessorTurma> page = professorTurmaService.findByFuncionarioAndAnoLectivo(usuario.getFuncionario(), paginaCorrente);
        List<ProfessorTurma> listaDeDisciplinaTurmas = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeDisciplinaTurmas", listaDeDisciplinaTurmas);
        mv.addObject("anoLectivos", anoLectivoService.findByEstadoIsTrue());
        return mv;
    }

    @GetMapping("/editarProfessorTurma/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("funcionarios/professorTurma");
        ProfessorTurma professorTurmaOptional = professorTurmaService.getOne(codigo);
        mv.addObject("professorTurma", professorTurmaOptional);
        mv.addObject("turmas", turmasService.getAll());
        mv.addObject("disciplinas", disciplinaService.getAll());
        mv.addObject("anoLectivos", anoLectivoService.findByEstadoIsTrue());
        mv.addObject("professores", funcionarioService.findByTipoFuncionarioAndEstadoIsTrue());
        return mv;

    }

    @GetMapping(value = "/eliminarProfessorTurma/{codigo}")
    @ResponseBody
    public boolean delete(@PathVariable("codigo") Long codigo) {
        professorTurmaService.delete(codigo);
        if (codigo == null) {
            return false;
        }
        return true;

    }

    @GetMapping("/detalhesTurma/{codigo}")
    public ModelAndView detalhesDisciplinaTurmas(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("notaAlunos/detalhesTurmaDisciplina");
        professorTurma = professorTurmaService.getOne(codigo);
        List<Matricula> matriculas = new ArrayList<>();
        if (professorTurma != null) {
            matriculas = matriculaService.findByTurma(professorTurma.getTurma());
        }
        //ModelAndView mv = new ModelAndView("redirect:/funcionarios/listarDetalhesDisciplinaTurma");
        mv.addObject("professorTurma", professorTurma);
        mv.addObject("alunosTurma", matriculas);
        return mv;
    }

    @RequestMapping("/listarDetalhesDisciplinaTurma")
    public ModelAndView listarDetalhesDisciplinaTurma() {

        return listarDetalhesDisciplinaTurma(1);
    }

    @GetMapping("/listarDetalhesDisciplinaTurma/{pageNumber}")
    public ModelAndView listarDetalhesDisciplinaTurma(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("notaAlunos/detalhesTurmaDisciplina");
        return mv;
    }

    @GetMapping("/cadastrarNotaAluno")
    public ModelAndView carregarForm(NotaAluno notaAluno) {
        ModelAndView mv = new ModelAndView("notaAlunos/notaAluno");
        mv.addObject("matriculas", matriculaService.findByTurma(professorTurma.getTurma()));
        mv.addObject("professorTurma", professorTurma);
        return mv;
    }

    @PostMapping("/cadastrarNotaAluno")
    public ModelAndView cadastrarNotaAluno(@Valid @ModelAttribute NotaAluno notaAluno, BindingResult result, RedirectAttributes attribute) {
        if (result.hasErrors()) {
            return carregarForm(notaAluno);
        }
        try {
            notaAluno.setDisciplina(professorTurma.getDisciplina());
            notaAlunoService.addNew(notaAluno);
        } catch (NomeExistenteException e) {
            result.rejectValue("matricula", e.getMessage(), e.getMessage());
            return carregarForm(notaAluno);
        }
        attribute.addFlashAttribute("success", "Nota salva com sucesso!");
        return new ModelAndView("redirect:/funcionarios/cadastrarNotaAluno");

    }

    @RequestMapping("/listarNotaAlunos")
    public ModelAndView listarNotaAlunos() {
        return listarNotaAlunos(1);
    }

    @GetMapping("/listarNotaAlunos/{pageNumber}")
    public ModelAndView listarNotaAlunos(@PathVariable("pageNumber") int paginaCorrente) {
        ModelAndView mv = new ModelAndView("notaAlunos/listaNotaAlunos");
        Page<NotaAluno> page = notaAlunoService.findAllAlunoNotas(professorTurma.getTurma(),professorTurma.getDisciplina(), paginaCorrente);
        List<NotaAluno> listaDeNotaAlunos = page.getContent();
        long totalDeItens = page.getTotalElements();
        int totalDePaginas = page.getTotalPages();
        mv.addObject("paginaCorrente", paginaCorrente);
        mv.addObject("totalDeItens", totalDeItens);
        mv.addObject("totalDePaginas", totalDePaginas);
        mv.addObject("listaDeNotaAlunos", listaDeNotaAlunos);
        mv.addObject("professorTurma", professorTurma);
        return mv;
    }

    @GetMapping("/editarNotaAluno/{codigo}")
    public ModelAndView editarNotaAluno(@PathVariable("codigo") Long codigo) {
        ModelAndView mv = new ModelAndView("notaAlunos/notaAluno");
        Optional<NotaAluno> notaOptional = notaAlunoService.getOne(codigo);
        mv.addObject("matriculas", matriculaService.findByTurma(professorTurma.getTurma()));
        mv.addObject("professorTurma", professorTurma);
        mv.addObject("notaAluno", notaOptional.get());
        return mv;

    }
}
