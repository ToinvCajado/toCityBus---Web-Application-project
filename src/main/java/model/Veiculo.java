package com.mycompany.pi_passagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Número do veículo é obrigatório")
    @Size(max = 20)
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotBlank(message = "Placa é obrigatória")
    @Size(max = 10)
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @NotBlank(message = "Nome do motorista é obrigatório")
    @Size(max = 100)
    @Column(name = "motorista", nullable = false)
    private String motorista;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100)
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @NotNull(message = "Quantidade de poltronas é obrigatória")
    @Min(value = 1, message = "Deve ter ao menos 1 poltrona")
    @Max(value = 200, message = "Máximo de 200 poltronas")
    @Column(name = "qtd_poltronas", nullable = false)
    private Integer qtdPoltronas;
}