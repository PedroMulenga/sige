package com.ispi.projectoIspi;

import com.ispi.projectoIspi.security.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PEDRO P MULENGA
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/img/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/alunos/**", "/matriculas/**", "/inscricoes/**","/cursos/**","/turmas/**").hasRole("EFECTUAR_MATRICULAS")
                .antMatchers("/transportes/**", "/listarPagamentoTransporte").hasRole("VERIFICAR_PAGAMENTOS")
                .antMatchers("/emolumentos/**").hasRole("REGISTAR_PAGAMENTOS")
                .antMatchers("/myPayments").hasRole("VERIFICAR_RELATORIOS")
                .antMatchers("/notas/**").hasRole("CONSULTAR_NOTAS")
                .antMatchers("/funcionarios/cadastrarProfessorTurma", "/funcionarios/listarProfessorTurmas",
                        "/funcionarios/eliminarProfessorTurma", "/funcionarios/editarProfessorTurma","/funcionarios/cadastrarFuncionarios",
                        "/funcionarios/listarFuncionarios","/funcionarios/editarFuncionario","/funcionarios/eliminarFuncionario").hasRole("CADASTRAR_FUNCIONARIOS")
                .antMatchers("/funcionarios/detalhesTurma", "/funcionarios/listarDetalhesDisciplinaTurma", "/funcionarios/listarDisciplinaTurmas",
                        "/funcionarios/cadastrarNotaAluno", "/funcionarios/editarNotaAluno", "/funcionarios/listarNotaAlunos").hasRole("LANCAR_NOTAS")
                .antMatchers("/paginas/**", "/usuarios/**").hasRole("CADASTRAR_USUARIOS")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
                .and()
                .sessionManagement().maximumSessions(1)
                .expiredUrl("/login")
                .and();
    }

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
