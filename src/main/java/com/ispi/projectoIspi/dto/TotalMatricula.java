package com.ispi.projectoIspi.dto;

/**
 *
 * @author PEDRO P MULENGA
 */
public class TotalMatricula {

    private String ano;
    private Integer totalMatricula;

    public TotalMatricula() {

    }

    public TotalMatricula(String ano, Integer totalMatricula) {
        this.ano = ano;
        this.totalMatricula = totalMatricula;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getTotalMatricula() {
        return totalMatricula;
    }

    public void setTotalMatricula(Integer totalMatricula) {
        this.totalMatricula = totalMatricula;
    }

}
