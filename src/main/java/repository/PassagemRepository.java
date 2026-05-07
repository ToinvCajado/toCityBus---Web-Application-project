/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import model.Passagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Integer> {

    // Para o requisito: Consultar passagens vendidas a um roteiro
    @Query("SELECT p FROM Passagem p WHERE p.cidadeOrigem.idCidade = :origem AND p.cidadeDestino.idCidade = :destino")
    List<Passagem> findByRoteiro(@Param("origem") String origem, @Param("destino") String destino);

    // Para o requisito: Consultar o faturamento dado um período específico
    @Query("SELECT SUM(p.valorPassagem) FROM Passagem p WHERE p.dataSaida BETWEEN :inicio AND :fim")
    BigDecimal calcularFaturamento(@Param("inicio") Date inicio, @Param("fim") Date fim);

    // Para validar se a poltrona já foi vendida (Requisito: O sistema deve acusar se já foi vendida)
    boolean existsByVeiculoIdAndDataSaidaAndHoraSaidaAndPoltrona(Long veiculoId, Date dataSaida, String horaSaida, int poltrona);
}