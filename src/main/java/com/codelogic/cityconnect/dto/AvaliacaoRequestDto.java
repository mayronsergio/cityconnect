package com.codelogic.cityconnect.dto;

import com.codelogic.cityconnect.exception.BusinessExcetion;
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

    private int nota;

    public void setNota(int nota){
        if (nota < 0 || nota > 5){
            throw new BusinessExcetion("Nota deve ser entre 0 e 5.");
        }
        this.nota = nota;
    }
}
