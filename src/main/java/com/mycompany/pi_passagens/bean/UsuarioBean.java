package com.mycompany.pi_passagens.bean;

import com.mycompany.pi_passagens.model.Usuario;
import com.mycompany.pi_passagens.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component("usuarioBean")
public class UsuarioBean implements Serializable {

    @Autowired
    private UsuarioService usuarioService;

    private List<Usuario> usuarios;
    private Usuario usuarioSelecionado;

    @PostConstruct
    public void init() {
        usuarioSelecionado = new Usuario();
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        usuarios = usuarioService.listarTodos();
    }

    public void salvar() {
        try {
            usuarioService.salvar(usuarioSelecionado);
            addMsg(FacesMessage.SEVERITY_INFO, "Usuário salvo com sucesso!");
            usuarioSelecionado = new Usuario();
            carregarUsuarios();
        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, e.getMessage());
        }
    }

    public void excluir(Usuario u) {
        try {
            usuarioService.excluir(u.getId());
            addMsg(FacesMessage.SEVERITY_INFO, "Usuário excluído!");
            carregarUsuarios();
        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, e.getMessage());
        }
    }

    public void novoUsuario() {
        usuarioSelecionado = new Usuario();
    }

    private void addMsg(FacesMessage.Severity sev, String txt) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(sev, txt, null));
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
}