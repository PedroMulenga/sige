/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.security;

import com.ispi.projectoIspi.Repository.UsuarioRepository;
import com.ispi.projectoIspi.model.Usuario;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class AppUserDetailsService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional= usuarioRepository.findByEmailIgnoreCaseAndEstadoIsTrue(email);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorrectos"));
       return new UsuarioSistema(usuario, getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<String> permissoes= usuarioRepository.findByPermissoes(usuario);
        permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority("ROLE_"+p.toUpperCase())));
        
        //usuario.getGrupos().get(0).getPermissoes().get(0).getNome();
       return authorities;
    }
    
}
