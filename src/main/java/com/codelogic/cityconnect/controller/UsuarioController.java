package com.codelogic.cityconnect.controller;

import com.codelogic.cityconnect.dto.UsuarioRequestDto;
import com.codelogic.cityconnect.dto.UsuarioResponseDto;
import com.codelogic.cityconnect.model.Usuario;
import com.codelogic.cityconnect.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private ModelMapper modelMapper;

    public UsuarioController(UsuarioService service, ModelMapper modelMapper) {
        this.usuarioService = service;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> entity = usuarioService.buscarPorId(id);
        if (entity.isPresent()) {
            UsuarioResponseDto usuarioResponseDto = modelMapper.map(entity.get(), UsuarioResponseDto.class);
            return ResponseEntity.ok(usuarioResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<UsuarioResponseDto> listarTodos() {
        return usuarioService.listarTodos().stream().map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public UsuarioResponseDto criar(@RequestBody @Valid UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = modelMapper.map(usuarioRequestDto, Usuario.class);
        return modelMapper.map(usuarioService.salvar(usuario), UsuarioResponseDto.class);
    }
}
