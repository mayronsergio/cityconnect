package com.codelogic.cityconnect.service;

import com.codelogic.cityconnect.dto.AvaliacaoRequestDto;
import com.codelogic.cityconnect.dto.AvaliacaoResponseDto;
import com.codelogic.cityconnect.dto.mapper.AvaliacaoMapper;
import com.codelogic.cityconnect.model.Avaliacao;
import com.codelogic.cityconnect.repository.AvaliacaoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, AvaliacaoMapper avaliacaoMapper){
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    public Avaliacao salvar(AvaliacaoRequestDto avaliacaoDto){
        Avaliacao avaliacao = avaliacaoMapper.avaliacaoDtoToAvaliacao(avaliacaoDto);
        return avaliacaoRepository.save(avaliacao);
    }

    public List<AvaliacaoResponseDto> consultarTodas(Long estabelecimentoId) {

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByEstabelecimentoId(estabelecimentoId);

        return avaliacoes.stream()
                .map(avaliacao -> avaliacaoMapper.avaliacaoToavaliacaoResponseDto(avaliacao))
                .collect(Collectors.toList());
    }

    public AvaliacaoResponseDto consultarPorId(Long idAvaliacao, Long idEstabelecimento) {
        Avaliacao avaliacao = avaliacaoRepository.findByIdAndEstabelecimentoId(idAvaliacao, idEstabelecimento);
        return avaliacaoMapper.avaliacaoToavaliacaoResponseDto(avaliacao);
    }
}
