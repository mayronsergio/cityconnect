package com.codelogic.cityconnect.dto.mapper;

import com.codelogic.cityconnect.dto.EstabelecimentoRequestDto;
import com.codelogic.cityconnect.dto.EstabelecimentoResponseDto;
import com.codelogic.cityconnect.model.Estabelecimento;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class EstabelecimentoMapper {

    private ModelMapper modelMapper;


    public EstabelecimentoMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(configMapperModel);
        this.modelMapper.addMappings(configMapperDto);
    }

    PropertyMap<EstabelecimentoRequestDto, Estabelecimento> configMapperModel = new PropertyMap<EstabelecimentoRequestDto, Estabelecimento>() {
        @Override
        protected void configure() {
            map().getEndereco().setLogradouro(source.getLogradouro());
            map().getEndereco().setBairro(source.getBairro());
            map().getEndereco().setCep(source.getCep());
            map().getEndereco().setNumero(source.getNumero());
            map().getEndereco().setCidade(source.getCidade());
            map().getEndereco().setUf(source.getUf());
            map().getEndereco().setLinkMaps(source.getLinkMaps());
            skip(destination.getFotoPerfil());
            skip(destination.getFotosAmbiente());
        }
    };

    PropertyMap<Estabelecimento, EstabelecimentoResponseDto> configMapperDto = new PropertyMap<Estabelecimento, EstabelecimentoResponseDto>() {
        @Override
        protected void configure() {
            map().setFotoPerfil(source.getFotoPerfil().getBase64Data());
        }
    };

    public Estabelecimento estabelecimentoDtoToEstabelecimento(EstabelecimentoRequestDto estabelecimentoRequestDto){
        return  modelMapper.map(estabelecimentoRequestDto, Estabelecimento.class);
    }

    public EstabelecimentoResponseDto estabelecimentoToestabelecimentoResponseDto(Estabelecimento estabelecimento){
        return modelMapper.map(estabelecimento, EstabelecimentoResponseDto.class);
    }
}
