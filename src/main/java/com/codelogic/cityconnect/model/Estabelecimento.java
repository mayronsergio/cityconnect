package com.codelogic.cityconnect.model;

import com.codelogic.cityconnect.model.enums.TipoEstabelecimento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoEstabelecimento tipo;

    @Column(length = 1000)
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Foto fotoPerfil;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Foto> fotosAmbiente;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoes;

    public double calcularMediaNotas() {
        int numAvaliacoes = avaliacoes.size();
        if (numAvaliacoes == 0) {
            return 0.0;
        }

        double somaNotas = avaliacoes.stream().mapToDouble(Avaliacao::getNota).sum();
        return somaNotas / numAvaliacoes;
    }
}
