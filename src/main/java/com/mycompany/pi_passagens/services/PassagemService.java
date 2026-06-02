package com.mycompany.pi_passagens.services;

import com.mycompany.pi_passagens.model.Passagem;
import com.mycompany.pi_passagens.repository.PassagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        // 1. Origem e destino não podem ser iguais
        if (passagem.getCidadeOrigem().getIdCidade()
                .equals(passagem.getCidadeDestino().getIdCidade())) {
            throw new Exception("Origem e destino não podem ser iguais para a mesma passagem!");
        }

        // 2. Não permitir venda para datas passadas
        LocalDate hoje = LocalDate.now();
        LocalDate dataSaida = passagem.getDataSaida().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        if (dataSaida.isBefore(hoje)) {
            throw new Exception("Não é possível vender passagem para data anterior a hoje ("
                    + hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")!");
        }

        // 3. Número de poltrona não pode exceder a capacidade do veículo
        if (passagem.getPoltrona() > passagem.getVeiculo().getQtdPoltronas()) {
            throw new Exception("Poltrona " + passagem.getPoltrona()
                    + " inválida: o veículo possui apenas "
                    + passagem.getVeiculo().getQtdPoltronas() + " poltronas!");
        }

        // 4. Poltrona já vendida para este veículo/data/hora
        boolean jaVendida = repository.existsByVeiculoIdAndDataSaidaAndHoraSaidaAndPoltrona(
            passagem.getVeiculo().getId(),
            passagem.getDataSaida(),
            passagem.getHoraSaida(),
            passagem.getPoltrona()
        );
        if (jaVendida) {
            throw new Exception("Poltrona " + passagem.getPoltrona()
                    + " já vendida para este veículo na data "
                    + dataSaida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + " às " + passagem.getHoraSaida() + "!");
        }

        // 5. Veículo já alocado em outro roteiro no mesmo horário
        long conflito = repository.contarVeiculoEmOutroRoteiro(
            passagem.getVeiculo().getId(),
            passagem.getDataSaida(),
            passagem.getHoraSaida(),
            passagem.getCidadeOrigem().getIdCidade(),
            passagem.getCidadeDestino().getIdCidade()
        );
        if (conflito > 0) {
            throw new Exception("O veículo " + passagem.getVeiculo().getPlaca()
                    + " já está alocado em outro roteiro em "
                    + dataSaida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + " às " + passagem.getHoraSaida() + "!");
        }

        return repository.save(passagem);
    }

    public List<Passagem> listarPassagensPorRoteiro(String origem, String destino) {
        return repository.findByRoteiro(origem, destino);
    }

    public List<Passagem> listarPorRoteiroEData(String origem, String destino, Date data) {
        return repository.findByRoteiroEData(origem, destino, data);
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