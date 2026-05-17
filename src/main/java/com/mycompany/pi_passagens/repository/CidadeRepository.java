package com.mycompany.pi_passagens.repository;

import com.mycompany.pi_passagens.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, String> {
    List<Cidade> findByNomeCidadeContainingIgnoreCase(String nome);
}
