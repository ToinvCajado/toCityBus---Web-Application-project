package com.mycompany.pi_passagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(
    name = "passagem",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_veiculo_poltrona_data_rota",
            columnNames = {
                "veiculo_id", "poltrona", "data_saida",
                "cidade_origem_id", "cidade_destino_id"
            }
        )
    }
)
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passagem")
    private Long idPassagem;

    @NotNull(message = "Veículo é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @NotNull(message = "Poltrona é obrigatória")
    @Min(value = 1, message = "Poltrona deve ser maior que 0")
    @Column(name = "poltrona", nullable = false)
    private Integer poltrona;

    @NotNull(message = "Data de saída é obrigatória")
    @Column(name = "data_saida", nullable = false)
    private LocalDate dataSaida;

    @NotBlank(message = "Hora de saída é obrigatória")
    @Size(max = 5)
    @Column(name = "hora_saida", nullable = false, length = 5)
    private String horaSaida;

    @NotNull(message = "Cidade de origem é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cidade_origem_id", nullable = false)
    private Cidade cidadeOrigem;

    @NotNull(message = "Cidade de destino é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cidade_destino_id", nullable = false)
    private Cidade cidadeDestino;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "valor_passagem", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPassagem;
}