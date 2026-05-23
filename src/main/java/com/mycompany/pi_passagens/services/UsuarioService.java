package com.mycompany.pi_passagens.services;

import com.mycompany.pi_passagens.model.Usuario;
import com.mycompany.pi_passagens.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuário não encontrado: " + login));
        return User.builder()
            .username(u.getLogin())
            .password(u.getSenha())
            .roles(u.getRole().replace("ROLE_", ""))
            .build();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByLogin(usuario.getLogin()))
            throw new RuntimeException(
                "Já existe usuário com login: " + usuario.getLogin());
        usuario.setSenha(
            passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuário não encontrado.");
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}