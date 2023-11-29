package com.codelogic.cityconnect.dto;

import com.codelogic.cityconnect.model.Endereco;
import com.codelogic.cityconnect.model.enums.TipoEstabelecimento;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class EstabelecimentoResponseDto {
    private Long id;
    private String nome;
    private TipoEstabelecimento tipo;
    private String descricao;
    private double mediaNotas;
    private byte[] fotoPerfil;
//    private List<MultipartFile> fotosAmbiente;
    private Endereco endereco;
}