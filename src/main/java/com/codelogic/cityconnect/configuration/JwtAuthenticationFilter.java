package com.codelogic.cityconnect.configuration;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.codelogic.cityconnect.exception.CustomException;
import com.codelogic.cityconnect.model.Usuario;
import com.codelogic.cityconnect.repository.UsuarioRepository;
import com.codelogic.cityconnect.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UsuarioRepository usuariosRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken;

        String autorizationHeader = request.getHeader("Authorization");

        try {
            if (autorizationHeader != null) {
                jwtToken = autorizationHeader.replace("Bearer ", "");
                String email = this.jwtTokenService.getSubject(jwtToken);

                Usuario usuario = this.usuariosRepository.findByEmail(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            }
            filterChain.doFilter(request, response);

        } catch (TokenExpiredException ex) {
            CustomException customException = new CustomException("Acesso negado: O token de autenticacao expirou.", HttpServletResponse.SC_UNAUTHORIZED);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getOutputStream().println(customException.toJson());
        }

    }
}
