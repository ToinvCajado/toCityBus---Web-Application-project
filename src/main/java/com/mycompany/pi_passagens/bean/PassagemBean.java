package com.mycompany.pi_passagens.bean;

import com.mycompany.pi_passagens.model.Cidade;
import com.mycompany.pi_passagens.model.Passagem;
import com.mycompany.pi_passagens.model.Veiculo;
import com.mycompany.pi_passagens.services.CidadeService;
import com.mycompany.pi_passagens.services.PassagemService;
import com.mycompany.pi_passagens.services.VeiculoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import reports.JasperReports;

@Named("passagemBean")
@ViewScoped
public class PassagemBean implements Serializable {

    @Autowired private PassagemService service;
    @Autowired private CidadeService cidadeService;
    @Autowired private VeiculoService veiculoService;
    @Autowired(required = false) private JasperReports jasperReports; // Opcional para não travar o init

    private List<Passagem> passagens;
    private Passagem passagemSelecionada = new Passagem();
    private Long idVeiculoSelecionado;
    private String idOrigemSelecionada;
    private String idDestinoSelecionada;

    private List<Cidade> cidades;
    private List<Veiculo> veiculos;

    private LocalDate inicioFaturamento;
    private LocalDate fimFaturamento;
    private BigDecimal totalFaturamento;
    private List<Passagem> passagensFaturamento;

    private String idOrigemRoteiro;
    private String idDestinoRoteiro;
    private List<Passagem> passagensRoteiro;
    
    private LocalDate dataRoteiro;


    @PostConstruct
    public void init() {
        try {
            listar();
            if (cidadeService != null) this.cidades = cidadeService.listarTodas();
            if (veiculoService != null) this.veiculos = veiculoService.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro no init do PassagemBean: " + e.getMessage());
        }
    }

    public void listar() {
        if (service != null) this.passagens = service.listarTodas();
    }

    public void calcularFaturamento() {
        if (inicioFaturamento == null || fimFaturamento == null) {
            addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Informe as duas datas.");
            return;
        }
        Date inicio = Date.from(inicioFaturamento.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim    = Date.from(fimFaturamento.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.totalFaturamento = service.consultarFaturamento(inicio, fim);
        this.passagensFaturamento = service.listarPorPeriodo(inicio, fim);
    }

    public void consultarRoteiro() {
        if (idOrigemRoteiro == null || idDestinoRoteiro == null) {
            addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione origem e destino.");
            return;
        }
        this.passagensRoteiro = service.listarPassagensPorRoteiro(idOrigemRoteiro, idDestinoRoteiro);
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters e Setters essenciais para as telas funcionarem
    public List<Passagem> getPassagens() { return passagens; }
    public Passagem getPassagemSelecionada() { return passagemSelecionada; }
    public void setPassagemSelecionada(Passagem p) { this.passagemSelecionada = p; }
    public Long getIdVeiculoSelecionado() { return idVeiculoSelecionado; }
    public void setIdVeiculoSelecionado(Long id) { this.idVeiculoSelecionado = id; }
    public String getIdOrigemSelecionada() { return idOrigemSelecionada; }
    public void setIdOrigemSelecionada(String id) { this.idOrigemSelecionada = id; }
    public String getIdDestinoSelecionada() { return idDestinoSelecionada; }
    public void setIdDestinoSelecionada(String id) { this.idDestinoSelecionada = id; }
    public List<Cidade> getCidades() { return cidades; }
    public List<Veiculo> getVeiculos() { return veiculos; }
    public LocalDate getInicioFaturamento() { return inicioFaturamento; }
    public void setInicioFaturamento(LocalDate d) { this.inicioFaturamento = d; }
    public LocalDate getFimFaturamento() { return fimFaturamento; }
    public void setFimFaturamento(LocalDate d) { this.fimFaturamento = d; }
    public BigDecimal getTotalFaturamento() { return totalFaturamento; }
    public List<Passagem> getPassagensFaturamento() { return passagensFaturamento; }
    public String getIdOrigemRoteiro() { return idOrigemRoteiro; }
    public void setIdOrigemRoteiro(String id) { this.idOrigemRoteiro = id; }
    public String getIdDestinoRoteiro() { return idDestinoRoteiro; }
    public void setIdDestinoRoteiro(String id) { this.idDestinoRoteiro = id; }
    public List<Passagem> getPassagensRoteiro() { return passagensRoteiro; }
        public LocalDate getDataRoteiro() { 
        return dataRoteiro; 
    }
    public void setDataRoteiro(LocalDate dataRoteiro) { 
        this.dataRoteiro = dataRoteiro; 
    }

}
