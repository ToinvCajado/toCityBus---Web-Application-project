package com.mycompany.pi_passagens.bean;

import com.mycompany.pi_passagens.model.Usuario;
import com.mycompany.pi_passagens.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

    @Autowired
    private UsuarioService usuarioService;

    private List<Usuario> usuarios;
    private Usuario usuarioSelecionado;

    public UsuarioBean() {
    }

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

            addMensagem(
                FacesMessage.SEVERITY_INFO,
                "Usuário salvo com sucesso!"
            );

            usuarioSelecionado = new Usuario();
            carregarUsuarios();

        } catch (Exception e) {

            addMensagem(
                FacesMessage.SEVERITY_ERROR,
                e.getMessage()
            );
        }
    }

    public void excluir(Usuario u) {
        try {

            usuarioService.excluir(u.getId());

            addMensagem(
                FacesMessage.SEVERITY_INFO,
                "Usuário excluído!"
            );

            carregarUsuarios();

        } catch (Exception e) {

            addMensagem(
                FacesMessage.SEVERITY_ERROR,
                e.getMessage()
            );
        }
    }

    private void addMensagem(
            FacesMessage.Severity sev,
            String txt) {

        FacesContext.getCurrentInstance()
                .addMessage(
                        null,
                        new FacesMessage(sev, txt, null)
                );
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(
            Usuario usuarioSelecionado) {

        this.usuarioSelecionado = usuarioSelecionado;
    }
}