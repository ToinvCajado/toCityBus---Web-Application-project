package com.mycompany.pi_passagens.services;

import com.mycompany.pi_passagens.model.Usuario;
import com.mycompany.pi_passagens.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));

        return User.builder()
            .username(usuario.getLogin())
            .password(usuario.getSenha())
            // Remove o prefixo ROLE_ porque o método .roles() adiciona o prefixo automaticamente por baixo dos panos
            .roles(usuario.getRole().replace("ROLE_", "")) 
            .build();
    }
}