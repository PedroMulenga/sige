/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Enum;

/**
 *
 * @author PEDRO P MULENGA
 */
public enum ListaPermissoes {
    EFECTUAR_INSCRICOES("Efectuar Inscrições"),
    EFECTUAR_MATRICULAS("Efectuar Matrículas"),
    REGISTAR_PAGAMENTOS("Registar Pagamentos"),
    VERIFICAR_PAGAMENTOS("Verificar Pagamentos"),
    CADASTRAR_FUNCIONARIOS("Cadastrar Funcionários"),
    CADASTRAR_ACESSORIOS("Cadastrar Acessórios"),
    VERIFICAR_RELATORIOS("Verificar Relatórios"),
    CADASTRAR_USUARIOS("Cadastrar Usuários");
    private String listaPermissao;
        
    private ListaPermissoes(String listaPermissao) {
        this.listaPermissao = listaPermissao;

    }
    
}
