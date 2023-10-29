package com.codelogic.cityconnect.controller;

import com.codelogic.cityconnect.dto.CredentialRequestDto;
import com.codelogic.cityconnect.dto.JwtResponseDto;
import com.codelogic.cityconnect.model.Usuario;
import com.codelogic.cityconnect.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    @PostMapping("/auth")
    public JwtResponseDto login(@RequestBody CredentialRequestDto credentialRequestDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(credentialRequestDto.email(), credentialRequestDto.password());

        Authentication authentication = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        return new JwtResponseDto(jwtTokenService.gerarToken(usuario));
    }
}