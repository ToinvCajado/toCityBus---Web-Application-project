package com.mycompany.pi_passagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "Cargo é obrigatório")
    @Size(max = 50)
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @NotBlank(message = "Login é obrigatório")
    @Size(max = 50)
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    private String role = "ROLE_USER";
}