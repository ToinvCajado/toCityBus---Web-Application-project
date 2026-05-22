/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pi_passagens.beans;

import com.mycompany.pi_passagens.model.Usuario;
import com.mycompany.pi_passagens.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Named("usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    @Autowired
    private UsuarioService service;

    private Usuario usuarioSelecionado = new Usuario();
    private List<Usuario> usuarios;
    private String confirmacaoSenha;

    @PostConstruct
    public void init() {
        // Resgate manual caso o container do JSF não injete o Spring automaticamente
        if (this.service == null) {
            var servletContext = (jakarta.servlet.ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            var springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            this.service = springContext.getBean(UsuarioService.class);
        }

        listar();
    }

    public void novoUsuario() {
        usuarioSelecionado = new Usuario();
        confirmacaoSenha = null;
    }

    public String cadastrar() {
        if (!usuarioSelecionado.getSenha().equals(confirmacaoSenha)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro", "As senhas não coincidem."));
            return null;
        }
        try {
            service.salvar(usuarioSelecionado);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Usuário cadastrado com sucesso!"));
            return "/login?faces-redirect=true";
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
            return null;
        }
    }

    public void salvar() {
        try {
            service.salvar(usuarioSelecionado);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Usuário salvo com sucesso!"));
            usuarioSelecionado = new Usuario();
            confirmacaoSenha = null;
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void excluir(Usuario usuario) {
        try {
            service.excluir(usuario.getId());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sucesso", "Usuário removido."));
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                    "Não foi possível excluir o usuário."));
        }
    }

    public void listar() {
        usuarios = service.listarTodos();
    }

    public Usuario getUsuarioSelecionado() { return usuarioSelecionado; }
    public void setUsuarioSelecionado(Usuario u) { this.usuarioSelecionado = u; }
    public List<Usuario> getUsuarios() { return usuarios; }
    public String getConfirmacaoSenha() { return confirmacaoSenha; }
    public void setConfirmacaoSenha(String c) { this.confirmacaoSenha = c; }
}