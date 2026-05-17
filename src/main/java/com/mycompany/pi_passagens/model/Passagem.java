package com.mycompany.pi_passagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "passagem", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"veiculo_id", "poltrona", "data_saida", "hora_saida"})
})
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPassagem; // Conforme requisito: int

    @NotNull
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Min(1)
    private int poltrona;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "data_saida")
    private Date dataSaida;

    @NotBlank
    @Column(name = "hora_saida")
    private String horaSaida;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "origem_id")
    private Cidade cidadeOrigem;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Cidade cidadeDestino;

    @Column(name = "valor_passagem", precision = 10, scale = 2)
    private BigDecimal valorPassagem;
}