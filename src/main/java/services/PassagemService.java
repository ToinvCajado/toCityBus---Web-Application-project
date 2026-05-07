/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import model.Passagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PassagemRepository;
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
        // 1. Validação: Poltrona não pode ser superior ao limite do veículo
        if (passagem.getPoltrona() > passagem.getVeiculo().getQtdPoltronas()) {
            throw new Exception("Erro: O número da poltrona excede a capacidade do veículo!");
        }

        // 2. Validação: O sistema deve acusar se uma passagem já foi vendida
        boolean jaVendida = repository.existsByVeiculoIdAndDataSaidaAndHoraSaidaAndPoltrona(
                passagem.getVeiculo().getId(),
                passagem.getDataSaida(),
                passagem.getHoraSaida(),
                passagem.getPoltrona()
        );

        if (jaVendida) {
            throw new Exception("Erro: Esta poltrona já foi vendida para esta viagem!");
        }

        // 3. A passagem é gerada no momento da compra
        return repository.save(passagem);
    }

    public List<Passagem> listarPassagensPorRoteiro(String origem, String destino) {
        return repository.findByRoteiro(origem, destino);
    }

    public BigDecimal consultarFaturamento(Date inicio, Date fim) {
        BigDecimal faturamento = repository.calcularFaturamento(inicio, fim);
        return faturamento != null ? faturamento : BigDecimal.ZERO;
    }

    public void excluir(int id) {
        repository.deleteById(id);
    }
    
    public List<Passagem> listarTodas() {
        return repository.findAll();
    }
}