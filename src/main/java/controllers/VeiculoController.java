/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import model.Veiculo;
import services.VeiculoService;
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
public class VeiculoController implements Serializable {

    @Autowired
    private VeiculoService service;

    private Veiculo veiculo = new Veiculo();
    private List<Veiculo> veiculos;

    @PostConstruct
    public void init() {
        listar();
    }

    public void salvar() {
        service.salvar(veiculo);
        veiculo = new Veiculo();
        listar();
    }

    public void excluir(Long id) {
        service.excluir(id);
        listar();
    }

    public void listar() {
        veiculos = service.listarTodos();
    }
}