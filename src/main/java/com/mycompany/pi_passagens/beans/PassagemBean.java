package com.mycompany.pi_passagens.beans;

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
import org.springframework.web.context.support.WebApplicationContextUtils; // IMPORTANTE

@Named("passagemBean")
@ViewScoped
public class PassagemBean implements Serializable {

    @Autowired private PassagemService service;        
    @Autowired private CidadeService cidadeService;      
    @Autowired private VeiculoService veiculoService;    

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
    private LocalDate dataRoteiro;
    private List<Passagem> passagensRoteiro;

    @PostConstruct
    public void init() {
        // Resgata os 3 serviços necessários do contexto do Spring se estiverem nulos
        if (this.service == null || this.cidadeService == null || this.veiculoService == null) {
            var servletContext = (jakarta.servlet.ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            var springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            
            this.service = springContext.getBean(PassagemService.class);
            this.cidadeService = springContext.getBean(CidadeService.class);
            this.veiculoService = springContext.getBean(VeiculoService.class);
        }

        listar();
        cidades = cidadeService.listarTodas();
        veiculos = veiculoService.listarTodos();
    }

    public void listar() {
        passagens = service.listarTodas();
    }

    public void excluir(Passagem p) {
        try {
            service.excluir(p.getIdPassagem());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Passagem removida."));
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public String vender() {
        try {
            if (idVeiculoSelecionado != null) {
                passagemSelecionada.setVeiculo(veiculoService.buscarPorId(idVeiculoSelecionado));
            }
            if (idOrigemSelecionada != null) {
                passagemSelecionada.setCidadeOrigem(cidadeService.buscarPorId(idOrigemSelecionada));
            }
            if (idDestinoSelecionada != null) {
                passagemSelecionada.setCidadeDestino(cidadeService.buscarPorId(idDestinoSelecionada));
            }

            service.venderPassagem(passagemSelecionada);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Passagem gerada com sucesso!"));
            passagemSelecionada = new Passagem();
            idVeiculoSelecionado = null;
            idOrigemSelecionada = null;
            idDestinoSelecionada = null;
            return "/passagem/lista?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
            return null;
        }
    }

    public void calcularFaturamento() {
        if (inicioFaturamento == null || fimFaturamento == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Informe as duas datas."));
            return;
        }
        Date inicio = Date.from(inicioFaturamento.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim    = Date.from(fimFaturamento.atStartOfDay(ZoneId.systemDefault()).toInstant());
        totalFaturamento = service.consultarFaturamento(inicio, fim);
        passagensFaturamento = service.listarPorPeriodo(inicio, fim);
    }

    public void consultarRoteiro() {
        if (idOrigemRoteiro == null || idDestinoRoteiro == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione origem e destino."));
            return;
        }
        passagensRoteiro = service.listarPassagensPorRoteiro(idOrigemRoteiro, idDestinoRoteiro);
    }

    // Getters e Setters
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
    public LocalDate getDataRoteiro() { return dataRoteiro; }
    public void setDataRoteiro(LocalDate d) { this.dataRoteiro = d; }
    public List<Passagem> getPassagensRoteiro() { return passagensRoteiro; }
}