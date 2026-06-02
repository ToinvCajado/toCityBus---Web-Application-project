package com.mycompany.pi_passagens.bean;

import com.mycompany.pi_passagens.model.*;
import com.mycompany.pi_passagens.services.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import static jakarta.faces.application.FacesMessage.SEVERITY_ERROR;
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
import org.springframework.web.context.support.WebApplicationContextUtils;
import reports.JasperReports;

@Named("passagemBean")
@ViewScoped
public class PassagemBean implements Serializable {

    @Autowired private PassagemService service;
    @Autowired private CidadeService cidadeService;
    @Autowired private VeiculoService veiculoService;
    @Autowired(required = false) private JasperReports jasperReports;

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
            if (this.service == null || this.cidadeService == null || this.veiculoService == null) {
                var servletContext = (jakarta.servlet.ServletContext)
                    FacesContext.getCurrentInstance().getExternalContext().getContext();
                var ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
                if (this.service == null) this.service = ctx.getBean(PassagemService.class);
                if (this.cidadeService == null) this.cidadeService = ctx.getBean(CidadeService.class);
                if (this.veiculoService == null) this.veiculoService = ctx.getBean(VeiculoService.class);
            }
            listar();
            this.cidades = cidadeService.listarTodas();
            this.veiculos = veiculoService.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro no init: " + e.getMessage());
        }
    }
    
public Date getHoje() {
    return new Date();
}

    public String vender() {
        try {
            Veiculo v = veiculoService.buscarPorId(idVeiculoSelecionado);
            Cidade origem = cidadeService.buscarPorId(idOrigemSelecionada);
            Cidade destino = cidadeService.buscarPorId(idDestinoSelecionada);

            passagemSelecionada.setVeiculo(v);
            passagemSelecionada.setCidadeOrigem(origem);
            passagemSelecionada.setCidadeDestino(destino);

            service.venderPassagem(passagemSelecionada);

            // Mensagem flash para exibir na tela de lista após redirecionamento
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.getExternalContext().getFlash().setKeepMessages(true);
            ctx.addMessage(null, new FacesMessage("Venda realizada com sucesso!",
                    "Passagem registrada para "
                    + origem.getNomeCidade() + " → " + destino.getNomeCidade() + "."));

            this.passagemSelecionada = new Passagem();
            return "/passagem/lista?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_ERROR, "Não foi possível realizar a venda", e.getMessage()));
            return null;
        }
    }

    public void listar() {
        if (service != null) this.passagens = service.listarTodas();
    }

    public void excluir(Passagem p) {
        try {
            service.excluir(p.getIdPassagem());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso", "Passagem excluída com sucesso!"));
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Erro", "Não foi possível excluir: " + e.getMessage()));
        }
    }

    public void calcularFaturamento() {
        if (inicioFaturamento == null || fimFaturamento == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Erro", "Informe o período."));
            return;
        }
        if (fimFaturamento.isBefore(inicioFaturamento)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Erro", "Data fim antes da data início."));
            return;
        }

        Date inicio = localDateToDate(inicioFaturamento);
        Date fim = Date.from(fimFaturamento.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        this.totalFaturamento = service.consultarFaturamento(inicio, fim);
        this.passagensFaturamento = service.listarPorPeriodo(inicio, fim);
    }

    public void consultarRoteiro() {
        if (idOrigemRoteiro == null || idOrigemRoteiro.isBlank() || idDestinoRoteiro == null || idDestinoRoteiro.isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Erro", "Informe origem e destino."));
            return;
        }
        if (idOrigemRoteiro.equals(idDestinoRoteiro)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Erro", "Origem e destino iguais."));
            return;
        }

        if (dataRoteiro != null) {
            this.passagensRoteiro = service.listarPorRoteiroEData(idOrigemRoteiro, idDestinoRoteiro, localDateToDate(dataRoteiro));
        } else {
            this.passagensRoteiro = service.listarPassagensPorRoteiro(idOrigemRoteiro, idDestinoRoteiro);
        }
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Getters e Setters
    public Passagem getPassagemSelecionada() { return passagemSelecionada; }
    public void setPassagemSelecionada(Passagem passagemSelecionada) { this.passagemSelecionada = passagemSelecionada; }
    public Long getIdVeiculoSelecionado() { return idVeiculoSelecionado; }
    public void setIdVeiculoSelecionado(Long idVeiculoSelecionado) { this.idVeiculoSelecionado = idVeiculoSelecionado; }
    public String getIdOrigemSelecionada() { return idOrigemSelecionada; }
    public void setIdOrigemSelecionada(String idOrigemSelecionada) { this.idOrigemSelecionada = idOrigemSelecionada; }
    public String getIdDestinoSelecionada() { return idDestinoSelecionada; }
    public void setIdDestinoSelecionada(String idDestinoSelecionada) { this.idDestinoSelecionada = idDestinoSelecionada; }
    public List<Veiculo> getVeiculos() { return veiculos; }
    public List<Cidade> getCidades() { return cidades; }
    public List<Passagem> getPassagens() { return passagens; }
    public LocalDate getInicioFaturamento() { return inicioFaturamento; }
    public void setInicioFaturamento(LocalDate inicioFaturamento) { this.inicioFaturamento = inicioFaturamento; }
    public LocalDate getFimFaturamento() { return fimFaturamento; }
    public void setFimFaturamento(LocalDate fimFaturamento) { this.fimFaturamento = fimFaturamento; }
    public BigDecimal getTotalFaturamento() { return totalFaturamento; }
    public void setTotalFaturamento(BigDecimal totalFaturamento) { this.totalFaturamento = totalFaturamento; }
    public List<Passagem> getPassagensFaturamento() { return passagensFaturamento; }
    public void setPassagensFaturamento(List<Passagem> passagensFaturamento) { this.passagensFaturamento = passagensFaturamento; }
    public String getIdOrigemRoteiro() { return idOrigemRoteiro; }
    public void setIdOrigemRoteiro(String idOrigemRoteiro) { this.idOrigemRoteiro = idOrigemRoteiro; }
    public String getIdDestinoRoteiro() { return idDestinoRoteiro; }
    public void setIdDestinoRoteiro(String idDestinoRoteiro) { this.idDestinoRoteiro = idDestinoRoteiro; }
    public List<Passagem> getPassagensRoteiro() { return passagensRoteiro; }
    public void setPassagensRoteiro(List<Passagem> passagensRoteiro) { this.passagensRoteiro = passagensRoteiro; }
    public LocalDate getDataRoteiro() { return dataRoteiro; }
    public void setDataRoteiro(LocalDate dataRoteiro) { this.dataRoteiro = dataRoteiro; }
}