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
    private Integer qtdPoltronas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Integer getQtdPoltronas() {
        return qtdPoltronas;
    }

    public void setQtdPoltronas(Integer qtdPoltronas) {
        this.qtdPoltronas = qtdPoltronas;
    }
    
    
}