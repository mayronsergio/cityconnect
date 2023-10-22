package com.codelogic.cityconnect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoRequestDto {

    private Long usuarioId;


    private Long estabelecimentoId;


    private String comentario;
}
