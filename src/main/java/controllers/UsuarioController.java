/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import model.Usuario;
import services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
@Getter @Setter
public class UsuarioController implements Serializable {

    @Autowired
    private UsuarioService service;

    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;

    @PostConstruct
    public void init() {
        listar();
    }

    public void salvar() {
        service.salvar(usuario);
        usuario = new Usuario();
        listar();
    }

    public void excluir(Long id) {
        service.excluir(id);
        listar();
    }

    public void listar() {
        usuarios = service.listarTodos();
    }
}