/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import model.Passagem;
import model.Cidade;
import model.Veiculo;
import services.PassagemService;
import services.CidadeService;
import services.VeiculoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@ViewScoped
@Getter @Setter
public class PassagemController implements Serializable {

    @Autowired
    private PassagemService service;
    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private VeiculoService veiculoService;

    private Passagem passagem = new Passagem();
    private List<Passagem> passagens;
    
    // Atributos para os filtros exigidos pelo professor
    private Date dataInicioFaturamento;
    private Date dataFimFaturamento;
    private BigDecimal faturamentoTotal;
    private String filtroOrigem;
    private String filtroDestino;

    @PostConstruct
    public void init() {
        listar();
    }

    public void vender() {
        try {
            service.venderPassagem(passagem);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Sucesso", "Passagem gerada com sucesso!"));
            passagem = new Passagem();
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void calcularFaturamento() {
        faturamentoTotal = service.consultarFaturamento(dataInicioFaturamento, dataFimFaturamento);
    }

    public void filtrarPorRoteiro() {
        passagens = service.listarPassagensPorRoteiro(filtroOrigem, filtroDestino);
    }

    public void listar() {
        passagens = service.listarTodas();
    }
}