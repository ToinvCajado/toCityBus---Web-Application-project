package com.mycompany.pi_passagens.beans;

import com.mycompany.pi_passagens.model.Veiculo;
import com.mycompany.pi_passagens.services.VeiculoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils; // IMPORTANTE

@Named("veiculoBean")
@ViewScoped
public class VeiculoBean implements Serializable {

    @Autowired
    private VeiculoService service;

    private Veiculo veiculoSelecionado = new Veiculo();
    private List<Veiculo> veiculos;

    @PostConstruct
    public void init() {
        // Resgate manual caso o container do JSF não injete o Spring automaticamente
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
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void excluir(Veiculo v) {
        try {
            service.excluir(v.getId());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Veículo removido."));
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível excluir: veículo pode ter passagens vinculadas."));
        }
    }

    public void listar() {
        veiculos = service.listarTodos();
    }

    public Veiculo getVeiculoSelecionado() { return veiculoSelecionado; }
    public void setVeiculoSelecionado(Veiculo v) { this.veiculoSelecionado = v; }
    public List<Veiculo> getVeiculos() { return veiculos; }
}