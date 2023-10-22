package com.codelogic.cityconnect.dto.mapper;

import com.codelogic.cityconnect.dto.AvaliacaoRequestDto;
import com.codelogic.cityconnect.dto.AvaliacaoResponseDto;
import com.codelogic.cityconnect.model.Avaliacao;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    private ModelMapper modelMapper;

    public AvaliacaoMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(configMapperModel);
        this.modelMapper.addMappings(configModelMapper);
    }

    PropertyMap<AvaliacaoRequestDto, Avaliacao> configMapperModel = new PropertyMap<AvaliacaoRequestDto, Avaliacao>() {
        @Override
        protected void configure() {
            map().getEstabelecimento().setId(source.getUsuarioId());
            map().getUsuario().setId(source.getEstabelecimentoId());
            skip(destination.getId());
        }
    };

    PropertyMap<Avaliacao, AvaliacaoResponseDto> configModelMapper = new PropertyMap<Avaliacao, AvaliacaoResponseDto>() {
        @Override
        protected void configure() {
            map().setId(source.getId());
            map().setEstabelecimentoId(source.getEstabelecimento().getId());
            map().setUsuarioId(source.getUsuario().getId());
            map().setComentario(source.getComentario());
        }
    };

    public Avaliacao avaliacaoDtoToAvaliacao(AvaliacaoRequestDto avaliacaoDto){
        return modelMapper.map(avaliacaoDto, Avaliacao.class);
    }

    public AvaliacaoResponseDto avaliacaoToavaliacaoResponseDto(Avaliacao avaliacao){
        return modelMapper.map(avaliacao, AvaliacaoResponseDto.class);
    }
}
