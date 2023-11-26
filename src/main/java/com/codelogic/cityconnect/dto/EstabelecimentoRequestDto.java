package com.codelogic.cityconnect.dto;

import com.codelogic.cityconnect.model.Endereco;
import com.codelogic.cityconnect.model.enums.TipoEstabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class EstabelecimentoRequestDto{

    @NotBlank(message = "Por favor, informe seu nome.")
    private String nome;
    private TipoEstabelecimento tipo;
    private String descricao;
    @NotNull(message = "Foto de perfil não pode estar vazia.")
    private MultipartFile fotoPerfil;
    private List<MultipartFile> fotosAmbiente;
    @NotBlank(message = "Por favor, informe seu logradouro.")
    private String logradouro;
    @NotBlank(message = "Por favor, informe seu bairro.")
    private String bairro;
    @NotBlank(message = "Por favor, informe o cep de sua cidade.")
    private String cep;
    private String numero;
    @NotBlank(message = "Por favor, informe o nome da sua cidade.")
    private String cidade;
    @NotBlank(message = "Por favor, informe a uf do seu estado.")
    private String uf;
    private String linkMaps;
}
