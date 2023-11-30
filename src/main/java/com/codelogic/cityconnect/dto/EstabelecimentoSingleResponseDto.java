package com.codelogic.cityconnect.dto;

import com.codelogic.cityconnect.model.Endereco;
import com.codelogic.cityconnect.model.enums.TipoEstabelecimento;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstabelecimentoSingleResponseDto {
    private Long id;
    private String nome;
    private TipoEstabelecimento tipo;
    private String descricao;
    private double mediaNotas;
    private byte[] fotoPerfil;
    private List<byte[]> fotosAmbiente;
    private Endereco endereco;
    private List<AvaliacaoResponseDto> avaliacoes;
}
