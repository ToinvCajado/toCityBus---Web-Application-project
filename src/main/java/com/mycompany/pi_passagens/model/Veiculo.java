package com.mycompany.pi_passagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Número é obrigatório")
    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @NotBlank(message = "Placa é obrigatória")
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @NotBlank(message = "Motorista é obrigatório")
    private String motorista;

    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_compra")
    private Date dataCompra;

    @NotNull(message = "Quantidade de poltronas é obrigatória")
    @Min(1)
    @Column(name = "qtd_poltronas", nullable = false)
    private int qtdPoltronas;
}