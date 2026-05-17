package com.mycompany.pi_passagens.beans;

import com.mycompany.pi_passagens.model.Cidade;
import com.mycompany.pi_passagens.services.CidadeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils; // IMPORTANTE

@Named("cidadeBean")
@ViewScoped
public class CidadeBean implements Serializable {

    @Autowired 
    private CidadeService service;

    private Cidade cidadeSelecionada = new Cidade();
    private List<Cidade> cidades;
    private String termoBusca;

    @PostConstruct
    public void init() {
        // Resgate manual caso o container do JSF não injete o Spring automaticamente
        if (this.service == null) {
            var servletContext = (jakarta.servlet.ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            var springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            this.service = springContext.getBean(CidadeService.class);
        }
        
        listar();
    }

    public void novaCidade() {
        cidadeSelecionada = new Cidade();
    }

    public void prepararEdicao(Cidade cidade) {
        cidadeSelecionada = new Cidade(cidade.getIdCidade(), cidade.getNomeCidade(), cidade.getUf());
    }

    public void salvar() {
        try {
            service.salvar(cidadeSelecionada);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Cidade salva com sucesso!"));
            cidadeSelecionada = new Cidade();
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void excluir(Cidade cidade) {
        try {
            service.excluir(cidade.getIdCidade());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Cidade removida."));
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível excluir: cidade pode estar vinculada a passagens."));
        }
    }

    public void buscar() {
        if (termoBusca == null || termoBusca.isBlank()) {
            listar();
        } else {
            cidades = service.buscarPorNome(termoBusca);
        }
    }

    public void listar() {
        cidades = service.listarTodas();
        termoBusca = null;
    }

    public Cidade getCidadeSelecionada() { return cidadeSelecionada; }
    public void setCidadeSelecionada(Cidade c) { this.cidadeSelecionada = c; }
    public List<Cidade> getCidades() { return cidades; }
    public String getTermoBusca() { return termoBusca; }
    public void setTermoBusca(String t) { this.termoBusca = t; }
}