/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Enum;

import com.ispi.projectoIspi.Repository.UsuarioRepository;

/**
 *
 * @author PEDRO P MULENGA
 */
public enum StatusUsuario {
    ATIVAR {
        @Override
        public void executar(Long[] codigos, UsuarioRepository usuarioRepository) {
            usuarioRepository.findByCodigoIn(codigos).forEach(u -> u.setEstado(true));
        }
    },
    DESATIVAR {
        @Override
        public void executar(Long[] codigos, UsuarioRepository usuarioRepository) {
            usuarioRepository.findByCodigoIn(codigos).forEach(u -> u.setEstado(false));
        }
    };

    public abstract void executar(Long[] codigos, UsuarioRepository usuarioRepository);

}
