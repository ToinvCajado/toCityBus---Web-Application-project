package com.mycompany.pi_passagens.bean;

import com.mycompany.pi_passagens.model.Veiculo;
import com.mycompany.pi_passagens.services.VeiculoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Named("veiculoBean")
@ViewScoped
public class VeiculoBean implements Serializable {

    @Autowired
    private VeiculoService service;

    private Veiculo veiculoSelecionado = new Veiculo();
    private List<Veiculo> veiculos;

    @PostConstruct
    public void init() {
        if (this.service == null) {
            var servletContext = (jakarta.servlet.ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            var springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            this.service = springContext.getBean(VeiculoService.class);
        }
        listar();
    }

    public void novoVeiculo() {
        veiculoSelecionado = new Veiculo();
    }

    public void prepararEdicao(Veiculo v) {
        veiculoSelecionado = v;
    }

    public void salvar() {
        try {
            service.salvar(veiculoSelecionado);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Veículo salvo com sucesso!"));
            veiculoSelecionado = new Veiculo();
            listar();
            PrimeFaces.current().ajax().addCallbackParam("saved", true);
        } catch (DataIntegrityViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar",
                    "Já existe um veículo cadastrado com esta placa ou número. Verifique os dados."));
            PrimeFaces.current().ajax().addCallbackParam("saved", false);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar", e.getMessage()));
            PrimeFaces.current().ajax().addCallbackParam("saved", false);
        }
    }

    public void excluir(Veiculo v) {
        try {
            service.excluir(v.getId());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Veículo \"" + v.getPlaca() + "\" removido com sucesso."));
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível excluir",
                    "O veículo \"" + v.getPlaca() + "\" possui passagens vinculadas e não pode ser removido."));
        }
    }

    public void listar() {
        veiculos = service.listarTodos();
    }

    public Veiculo getVeiculoSelecionado() { return veiculoSelecionado; }
    public void setVeiculoSelecionado(Veiculo v) { this.veiculoSelecionado = v; }
    public List<Veiculo> getVeiculos() { return veiculos; }
}