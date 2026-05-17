package com.mycompany.pi_passagens.services;

import com.mycompany.pi_passagens.model.Cidade;
import com.mycompany.pi_passagens.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<Cidade> buscarPorNome(String nome) {
        return repository.findByNomeCidadeContainingIgnoreCase(nome);
    }
}
