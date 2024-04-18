/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.model;

import com.ispi.projectoIspi.validadores.Strings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author PEDRO P MULENGA
 */
@SuppressWarnings("serial")
@Entity
public class NotaAluno extends GenericDomin {

    private Integer mac_um;
    private Integer npp_um;
    private Integer npt_um;
    private Integer mac_dois;
    private Integer npp_dois;
    private Integer npt_dois;
    private Integer mac_tres;
    private Integer npp_tres;
    private Integer npt_tres;
    private Integer medtrimestral_um = 0;
    private Integer medtrimestral_dois = 0;
    private Integer medtrimestral_tres = 0;
    private Integer medfinal=0;
    private String situacao;
    @ManyToOne
    @JoinColumn(name = "codigo_matricula", nullable = false)
    private Matricula matricula;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Disciplina disciplina;

    public Integer getMac_um() {
        return mac_um;
    }

    public void setMac_um(Integer mac_um) {
        this.mac_um = mac_um;
    }

    public Integer getNpp_um() {
        return npp_um;
    }

    public void setNpp_um(Integer npp_um) {
        this.npp_um = npp_um;
    }

    public Integer getNpt_um() {
        return npt_um;
    }

    public void setNpt_um(Integer npt_um) {
        this.npt_um = npt_um;
    }

    public Integer getMac_dois() {
        return mac_dois;
    }

    public void setMac_dois(Integer mac_dois) {
        this.mac_dois = mac_dois;
    }

    public Integer getNpp_dois() {
        return npp_dois;
    }

    public void setNpp_dois(Integer npp_dois) {
        this.npp_dois = npp_dois;
    }

    public Integer getNpt_dois() {
        return npt_dois;
    }

    public void setNpt_dois(Integer npt_dois) {
        this.npt_dois = npt_dois;
    }

    public Integer getMac_tres() {
        return mac_tres;
    }

    public void setMac_tres(Integer mac_tres) {
        this.mac_tres = mac_tres;
    }

    public Integer getNpp_tres() {
        return npp_tres;
    }

    public void setNpp_tres(Integer npp_tres) {
        this.npp_tres = npp_tres;
    }

    public Integer getNpt_tres() {
        return npt_tres;
    }

    public void setNpt_tres(Integer npt_tres) {
        this.npt_tres = npt_tres;
    }

    public Integer getMedtrimestral_um() {
        return medtrimestral_um;
    }

    public void setMedtrimestral_um(Integer medtrimestral_um) {
        this.medtrimestral_um = medtrimestral_um;
    }

    public Integer getMedtrimestral_dois() {
        return medtrimestral_dois;
    }

    public void setMedtrimestral_dois(Integer medtrimestral_dois) {
        this.medtrimestral_dois = medtrimestral_dois;
    }

    public Integer getMedtrimestral_tres() {
        return medtrimestral_tres;
    }

    public void setMedtrimestral_tres(Integer medtrimestral_tres) {
        this.medtrimestral_tres = medtrimestral_tres;
    }

    public Integer getMedfinal() {
        return medfinal;
    }

    public void setMedfinal(Integer medfinal) {
        this.medfinal = medfinal;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

}
