package com.codelogic.cityconnect.repository;

import com.codelogic.cityconnect.dto.AvaliacaoResponseDto;
import com.codelogic.cityconnect.model.Avaliacao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends GenericRepository<Avaliacao, Long> {
    List<Avaliacao> findByEstabelecimentoId(Long estabelecimentoId);

    Avaliacao findByIdAndEstabelecimentoId(Long idEstabelecimento, Long idAvaliacao);
}
