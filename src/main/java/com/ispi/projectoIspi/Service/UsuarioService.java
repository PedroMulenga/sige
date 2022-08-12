/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Service;

import com.ispi.projectoIspi.Enum.StatusUsuario;
import com.ispi.projectoIspi.ExceptionMessages.BiUsuarioExistenteException;
import com.ispi.projectoIspi.ExceptionMessages.SenhaObrigatoriaNovoUsuario;
import com.ispi.projectoIspi.ExceptionMessages.EmailUsuarioExistenteException;
import com.ispi.projectoIspi.Repository.UsuarioRepository;
import com.ispi.projectoIspi.model.Usuario;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author PEDRO P MULENGA
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<Usuario> getAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Page<Usuario> findAll(int pageNumber) {
        Sort sort = Sort.by("nomeUsuario").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return usuarioRepository.findAll(pageable);
    }

    public Page<Usuario> totalUsuario() {
        Pageable pageable = PageRequest.of(0, 10);
        return usuarioRepository.totalDeUsuarios(pageable);
    }

    public Optional<Usuario> getOne(Long codigo) {
        return usuarioRepository.findById(codigo);

    }

    @Transactional
    public void addNew(Usuario usuario) {
        Optional<Usuario> emailExistente = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
        Optional<Usuario> biExistente = usuarioRepository.findByBiIgnoreCase(usuario.getBi());
        if (emailExistente.isPresent() && !emailExistente.get().equals(usuario)) {
            throw new EmailUsuarioExistenteException("Este Email já se encontra registado no ISPI Payment!");
        }
        if (biExistente.isPresent() && !biExistente.get().equals(usuario)) {
            throw new BiUsuarioExistenteException("Este B.I já se encontra registado no ISPI Payment!");
        }
        if (usuario.isNovo() && usuario.getSenha() == "") {
            throw new SenhaObrigatoriaNovoUsuario("A senha é obrigatória para novo usuário!");

        }
        if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(this.bCryptPasswordEncoder.encode(usuario.getSenha()));
        } else if (StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(emailExistente.get().getSenha());
        }
        if(StringUtils.isEmpty(usuario.getNomeImagen())){
            usuario.setNomeImagen("userDefault.png");
        }
        usuario.setConfirmacaoSenha(usuario.getSenha());
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmailIgnoreCase(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    public void delete(Long codigo) {
        usuarioRepository.deleteById(codigo);

    }

    @Transactional
    public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
        statusUsuario.executar(codigos, usuarioRepository);
    }

}
