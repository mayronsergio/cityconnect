package com.codelogic.cityconnect.model;

import com.codelogic.cityconnect.dto.AvaliacaoResponseDto;
import com.codelogic.cityconnect.model.enums.TipoEstabelecimento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    	if (avaliacoes == null || avaliacoes.isEmpty()) {
            return 0.0;
        }

        int numAvaliacoes = avaliacoes.size();
        double somaNotas = avaliacoes.stream().mapToDouble(Avaliacao::getNota).sum();
        
        return somaNotas / numAvaliacoes;
    }

    public List<AvaliacaoResponseDto> obterAvaliacoesSimplificadas() {
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return Collections.emptyList();
        }

        return avaliacoes.stream()
                .map(avaliacao -> new AvaliacaoResponseDto(avaliacao.getUsuario().getNome(), avaliacao.getComentario()))
                .collect(Collectors.toList());
    }

    public List<byte[]> obterFotosAmbiente() {
        if (fotosAmbiente == null || fotosAmbiente.isEmpty()){
            return Collections.emptyList();
        }
        return fotosAmbiente.stream().map(Foto::getBase64Data).collect(Collectors.toList());
    }
}
