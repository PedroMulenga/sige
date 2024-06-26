/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.security;

import com.ispi.projectoIspi.model.Usuario;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author PEDRO P MULENGA
 */
public class UsuarioSistema extends User{
    private Usuario usuario;
    
    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario= usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
       
}
