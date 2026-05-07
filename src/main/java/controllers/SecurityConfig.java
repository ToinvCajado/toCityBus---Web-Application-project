/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. O JSF e o H2 precisam que o CSRF esteja desabilitado
            .csrf(csrf -> csrf.disable())
            
            // 2. Configuração de permissões
            .authorizeHttpRequests(auth -> auth
                // Libera o Console do Banco de Dados H2
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                // Libera recursos visuais (CSS, JS, Imagens do PrimeFaces)
                .requestMatchers(new AntPathRequestMatcher("/jakarta.faces.resource/**")).permitAll()
                // Por enquanto, libera tudo para você não ser bloqueado nas telas
                // Quando o colega fizer o login, mudamos para .authenticated()
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
            )
            
            // 3. Necessário para o H2 aparecer dentro do navegador (frames)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            
            // 4. Configuração básica de Login (usará o formulário padrão do Spring por enquanto)
            .formLogin(form -> form.permitAll())
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}