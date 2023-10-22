package com.codelogic.cityconnect.model;

import com.codelogic.cityconnect.model.enums.TipoEstabelecimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    private String descricao;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Foto fotoPerfil;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Foto> fotosAmbiente;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Endereco endereco;
}
