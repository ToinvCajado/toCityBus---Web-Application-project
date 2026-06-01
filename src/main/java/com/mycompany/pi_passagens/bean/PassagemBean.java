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
import org.springframework.web.context.support.WebApplicationContextUtils; // Import adicionado
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
            // Fallback: busca manualmente se @Autowired não injetou
            if (this.service == null || this.cidadeService == null || this.veiculoService == null) {
                var servletContext = (jakarta.servlet.ServletContext)
                    FacesContext.getCurrentInstance().getExternalContext().getContext();
                var ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
                if (this.service == null)        this.service = ctx.getBean(PassagemService.class);
                if (this.cidadeService == null)  this.cidadeService = ctx.getBean(CidadeService.class);
                if (this.veiculoService == null) this.veiculoService = ctx.getBean(VeiculoService.class);
            }
            
            listar();
            this.cidades = cidadeService.listarTodas();
            this.veiculos = veiculoService.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro no init do PassagemBean: " + e.getMessage());
        }
    }

    public void listar() {
        if (service != null) this.passagens = service.listarTodas();
    }
    
    // ... restante dos seus métodos (listar, calcularFaturamento, etc) continua igual ...
    
    // Getters e Setters continuam iguais
}