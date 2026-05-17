package com.mycompany.pi_passagens;

import com.mycompany.pi_passagens.model.Usuario;
import com.mycompany.pi_passagens.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PassagensApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PassagensApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PassagensApp.class, args);
    }

    /**
     * Cria um usuário admin na primeira vez que o sistema rodar.
     * Login: admin | Senha: 123
     */
    @Bean
    CommandLineRunner seedAdmin(UsuarioRepository repo, PasswordEncoder enc) {
        return args -> {
            if (repo.count() == 0) {
                Usuario u = new Usuario();
                u.setNome("Administrador");
                u.setCargo("Admin");
                u.setLogin("admin");
                u.setSenha(enc.encode("123")); // Criptografia correta da senha inicial
                u.setEmail("admin@tocitybus.com");
                u.setRole("ROLE_ADMIN"); // CORRIGIDO: De USER para ADMIN para garantir acessos totais
                repo.save(u);
                System.out.println("==> Usuário admin criado com sucesso. Login: admin | Senha: 123");
            }
        };
    }
}