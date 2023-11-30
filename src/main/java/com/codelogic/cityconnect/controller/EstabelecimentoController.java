package com.codelogic.cityconnect.controller;

import com.codelogic.cityconnect.dto.EstabelecimentoRequestDto;
import com.codelogic.cityconnect.dto.EstabelecimentoResponseDto;
import com.codelogic.cityconnect.dto.EstabelecimentoSingleResponseDto;
import com.codelogic.cityconnect.dto.mapper.EstabelecimentoMapper;
import com.codelogic.cityconnect.model.Estabelecimento;
import com.codelogic.cityconnect.model.Foto;
import com.codelogic.cityconnect.service.EstabelecimentoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    private final EstabelecimentoService estabelecimentoService;

    private EstabelecimentoMapper estabelecimentoMapper;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService, EstabelecimentoMapper estabelecimentoMapper) {
        this.estabelecimentoService = estabelecimentoService;
        this.estabelecimentoMapper = estabelecimentoMapper;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public EstabelecimentoResponseDto criar(@ParameterObject @Valid @ModelAttribute EstabelecimentoRequestDto entity) throws IOException {
        return estabelecimentoService.salvar(entity);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/{idEstabelecimento}/adicionarFotoAmbiente", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void adicionarFotoAmbiente(@PathVariable(value = "idEstabelecimento") Long idEstabelecimento, @Parameter @RequestParam MultipartFile fotoAmbiente) throws IOException {
        estabelecimentoService.adicionarFotoAmbiente(idEstabelecimento, fotoAmbiente);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoSingleResponseDto> buscarPorId(@PathVariable Long id) {
        Optional<Estabelecimento> entity = estabelecimentoService.buscarPorId(id);
        if (entity.isPresent()) {
            EstabelecimentoSingleResponseDto estabelecimentoResponseDto = estabelecimentoMapper.estabelecimentoToEstabelecimentoSingleResponseDto(entity.get());
            return ResponseEntity.ok(estabelecimentoResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{estabelecimentoId}/fotos-ambiente")
    public List<Foto> getFotosAmbiente(@PathVariable Long estabelecimentoId) {
        return estabelecimentoService.getFotosAmbiente(estabelecimentoId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<EstabelecimentoResponseDto> listarTodos() {
        return estabelecimentoService.listarTodos().stream().map(estabelecimento -> estabelecimentoMapper.estabelecimentoToestabelecimentoResponseDto(estabelecimento)).collect(Collectors.toList());
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
//        buscarPorId(id);
//        estabelecimentoService.deletarPorId(id);
//        return ResponseEntity.noContent().build();
//    }
}
