package com.codelogic.cityconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponseDto {

    private Long id;

    private Long usuarioId;

    private Long estabelecimentoId;

    private String comentario;
}
