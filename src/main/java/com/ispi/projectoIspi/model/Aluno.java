/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.Enum.Genero;
import com.ispi.projectoIspi.Enum.SituacaoMatricula;
import com.ispi.projectoIspi.validadores.Strings;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class Aluno extends GenericDomin {

    @Strings
    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    private String nome;
    @Strings
    @Column(length = 30, nullable = false)
    @NotBlank(message = "O campo sobrenome é obrigatório!")
    private String sobrenome;
    @Column(length = 16, nullable = false)
    @NotBlank(message = "O campo bi é obrigatório!")
    private String bi;
    @NotNull(message = "Digite a data de Nascimento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    @Column(length = 10)
    @NotNull(message = "O campo telefone é obrigatório!")
    private Integer telefone;
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O género é obrigatório!")
    private Genero genero;
    @Column(nullable = false)
    @NotBlank(message = "O campo Morada é obrigatório!")
    private String bairro;
    @NotNull(message = "Selecione a Naturalidade!")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Provincia provincia;
    @ManyToOne()
    @JoinColumn(nullable = false)
    private Municipio municipio;
    @Temporal(TemporalType.DATE)
    private Date dataRegisto = new Date();
    @Column(length = 10)
    private String numCRM;
    @Strings
    @Column(length = 200, nullable = false)
    private String nomePai;
    @Strings
    @Column(length = 200, nullable = false)
    private String nomeMae;
    private String nomeImagen;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi.toUpperCase();
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public String getNumCRM() {
        return numCRM;
    }

    public void setNumCRM(String numCRM) {
        this.numCRM = numCRM;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomeImagen() {
        return nomeImagen;
    }

    public void setNomeImagen(String nomeImagen) {
        this.nomeImagen = nomeImagen;
    }

}
