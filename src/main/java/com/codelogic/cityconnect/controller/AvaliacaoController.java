package com.codelogic.cityconnect.controller;

import com.codelogic.cityconnect.dto.AvaliacaoRequestDto;
import com.codelogic.cityconnect.dto.AvaliacaoResponseDto;
import com.codelogic.cityconnect.dto.mapper.AvaliacaoMapper;
import com.codelogic.cityconnect.model.Avaliacao;
import com.codelogic.cityconnect.service.AvaliacaoService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    
    private AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoController(AvaliacaoService avaliacaoService,AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public AvaliacaoResponseDto salvar(@RequestBody AvaliacaoRequestDto avaliacaoDto) {
        return avaliacaoMapper.avaliacaoToavaliacaoResponseDto(avaliacaoService.salvar(avaliacaoDto)) ;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/estabelecimetos/{id}")
    public List<AvaliacaoResponseDto> consultarTodas(@PathVariable(value = "id") Long estabelecimentoId) {
        return avaliacaoService.consultarTodas(estabelecimentoId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{idAvaliacao}/estabelecimetos/{idEstabelecimento}")
    public AvaliacaoResponseDto consultarPorId(@PathVariable(value = "idAvaliacao") Long idAvaliacao, @PathVariable(value = "idEstabelecimento") Long idEstabelecimento) {
        return avaliacaoService.consultarPorId(idAvaliacao, idEstabelecimento);
    }
}
