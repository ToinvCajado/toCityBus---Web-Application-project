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
    private Integer idPassagem; // Conforme requisito: int

    @NotNull
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Min(1)
    private Integer poltrona;

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

    public Integer getIdPassagem() {
        return idPassagem;
    }

    public void setIdPassagem(Integer idPassagem) {
        this.idPassagem = idPassagem;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getPoltrona() {
        return poltrona;
    }

    public void setPoltrona(Integer poltrona) {
        this.poltrona = poltrona;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public Cidade getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(Cidade cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public BigDecimal getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(BigDecimal valorPassagem) {
        this.valorPassagem = valorPassagem;
    }
    
    
}