package com.mycompany.pi_passagens.services;

import com.mycompany.pi_passagens.model.Veiculo;
import com.mycompany.pi_passagens.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    @Autowired
    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Veiculo salvar(Veiculo veiculo) {
        return repository.save(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return repository.findAll();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Veiculo buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
