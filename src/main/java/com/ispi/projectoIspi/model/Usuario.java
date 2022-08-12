/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.validadores.AtributoConfirmacaoSenha;
import com.ispi.projectoIspi.validadores.Bi;
import com.ispi.projectoIspi.validadores.Strings;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author PEDRO P MULENGA
 */
@AtributoConfirmacaoSenha(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "As senhas não são iguais")
@Entity
@DynamicUpdate
public class Usuario extends GenericDomin {

    @Strings
    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo nome do usuário é obrigatório!")
    private String nomeUsuario;
    @Column(unique = true)
    @NotBlank(message = "O E-mail é obrigatório!")
    @Email(message = "E-mail inválido")
    private String email;
    @Size(min = 5, message = "A senha deve conter pelo menos 5 caractter!")
    private String senha;
    @Transient
    private String confirmacaoSenha;
    @Bi
    @Column(length = 16, nullable = false)
    @NotBlank(message = "O campo B.I é obrigatório!")
    private String bi;
    @Column(length = 14)
    @Transient
    private String numeroEstudante;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Funcionario funcionario;
    @Temporal(TemporalType.DATE)
    private Date dataRegisto = new Date();
    @Transient
    private String estadoFormato;
    @Column(nullable = false)
    private boolean estado = true;
    @Size(min = 1, message = "Selecione um grupo")
    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"),
            inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
    private List<Grupo> grupos;
    // private String resetPasswordToken;
    private String nomeImagen;

    @PreUpdate
    private void preUpdate() {
        this.confirmacaoSenha = senha;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isNovo() {
        return codigo == null;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getEstadoFormato() {
        estadoFormato = "Inactivo";
        if (estado == true) {
            estadoFormato = "Activo";
        }
        return estadoFormato;
    }

    public void setEstadoFormato(String estadoFormato) {
        this.estadoFormato = estadoFormato;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        bi = bi.toUpperCase();
        this.bi = bi;
    }

    public String getNumeroEstudante() {
        return numeroEstudante;
    }

    public void setNumeroEstudante(String numeroEstudante) {
        this.numeroEstudante = numeroEstudante;
    }

    public String getNomeImagen() {
        return nomeImagen;
    }

    public void setNomeImagen(String nomeImagen) {
        this.nomeImagen = nomeImagen;
    }

}
