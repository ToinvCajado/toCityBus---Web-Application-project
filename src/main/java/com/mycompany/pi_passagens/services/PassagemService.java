package com.mycompany.pi_passagens.services;

import com.mycompany.pi_passagens.model.Passagem;
import com.mycompany.pi_passagens.repository.PassagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PassagemService {

    private final PassagemRepository repository;

    @Autowired
    public PassagemService(PassagemRepository repository) {
        this.repository = repository;
    }

    public Passagem venderPassagem(Passagem passagem) throws Exception {
        if (passagem.getPoltrona() > passagem.getVeiculo().getQtdPoltronas()) {
            throw new Exception("O número da poltrona excede a capacidade do veículo!");
        }

        boolean jaVendida = repository.existsByVeiculoIdAndDataSaidaAndHoraSaidaAndPoltrona(
            passagem.getVeiculo().getId(),
            passagem.getDataSaida(),
            passagem.getHoraSaida(),
            passagem.getPoltrona()
        );
        if (jaVendida) {
            throw new Exception("Esta poltrona já foi vendida para esta viagem!");
        }

        return repository.save(passagem);
    }

    public List<Passagem> listarPassagensPorRoteiro(String origem, String destino) {
        return repository.findByRoteiro(origem, destino);
    }

    public BigDecimal consultarFaturamento(Date inicio, Date fim) {
        BigDecimal fat = repository.calcularFaturamento(inicio, fim);
        return fat != null ? fat : BigDecimal.ZERO;
    }

    public List<Passagem> listarPorPeriodo(Date inicio, Date fim) {
        return repository.findByDataSaidaBetween(inicio, fim);
    }

    public void excluir(int id) {
        repository.deleteById(id);
    }

    public List<Passagem> listarTodas() {
        return repository.findAll();
    }
}