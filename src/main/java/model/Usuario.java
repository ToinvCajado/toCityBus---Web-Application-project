package model;

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
    private String nome;

    @NotBlank(message = "Cargo é obrigatório")
    private String cargo;

    @NotBlank(message = "Login é obrigatório")
    @Column(unique = true)
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Email
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    private String email;

    private String role = "ROLE_USER";
}