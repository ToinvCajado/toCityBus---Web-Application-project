package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @Column(name = "id_cidade", length = 10)
    private String idCidade; // Sigla IBGE definida pelo professor

    @NotBlank(message = "Nome da cidade é obrigatório")
    @Size(max = 100)
    @Column(name = "nome_cidade", nullable = false)
    private String nomeCidade;

    @NotBlank(message = "UF é obrigatória")
    @Size(min = 2, max = 2)
    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    public Cidade(String idCidade, String nomeCidade, String uf) {
        this.idCidade = idCidade;
        this.nomeCidade = nomeCidade;
        this.uf = uf;
    }
}