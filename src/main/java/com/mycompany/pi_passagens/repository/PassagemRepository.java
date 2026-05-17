package com.mycompany.pi_passagens.repository;

import com.mycompany.pi_passagens.model.Passagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Integer> {

    @Query("SELECT p FROM Passagem p WHERE p.cidadeOrigem.idCidade = :origem AND p.cidadeDestino.idCidade = :destino")
    List<Passagem> findByRoteiro(@Param("origem") String origem, @Param("destino") String destino);

    @Query("SELECT SUM(p.valorPassagem) FROM Passagem p WHERE p.dataSaida BETWEEN :inicio AND :fim")
    BigDecimal calcularFaturamento(@Param("inicio") Date inicio, @Param("fim") Date fim);

    boolean existsByVeiculoIdAndDataSaidaAndHoraSaidaAndPoltrona(
        Long veiculoId, Date dataSaida, String horaSaida, int poltrona);

    List<Passagem> findByDataSaidaBetween(Date inicio, Date fim);
}
