/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CidadeRepository;
import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository repository;

    @Autowired
    public CidadeService(CidadeRepository repository) {
        this.repository = repository;
    }

    public Cidade salvar(Cidade cidade) {
        return repository.save(cidade);
    }

    public List<Cidade> listarTodas() {
        return repository.findAll();
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }
    
    public Cidade buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }
}