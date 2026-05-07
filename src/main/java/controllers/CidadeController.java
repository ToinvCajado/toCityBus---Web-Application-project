/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import model.Cidade;
import services.CidadeService;
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
public class CidadeController implements Serializable {

    @Autowired
    private CidadeService service;

    private Cidade cidade = new Cidade();
    private List<Cidade> cidades;

    @PostConstruct
    public void init() {
        listar();
    }

    public void salvar() {
        service.salvar(cidade);
        cidade = new Cidade();
        listar();
    }

    public void excluir(String id) {
        service.excluir(id);
        listar();
    }

    public void listar() {
        cidades = service.listarTodas();
    }
}