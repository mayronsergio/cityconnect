package com.codelogic.cityconnect.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codelogic.cityconnect.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("com.portodoitaqui.secapi")
                .withClaim("role", usuario.getRole().name())
                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")))
                .withSubject(usuario.getEmail())
                .withExpiresAt(LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(secret)); //colocar secret segura
    }

    public String getSubject(String jwtToken) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("com.portodoitaqui.secapi")
                .build().verify(jwtToken)
                .getSubject();
    }

    public String getTokenHeader(HttpServletRequest request) {
        String autorizationHeader = request.getHeader("Authorization");
        String jwtToken = null;

        if (autorizationHeader != null) {
            jwtToken = autorizationHeader.replace("Bearer ", "");

        }
        return jwtToken;
    }

    public boolean verificarExpirado(String token) {
        Date expirantion = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("com.portodoitaqui.secapi")
                .build().verify(token)
                .getExpiresAt();
        Date currentDate = new Date();
        return expirantion.before(currentDate);
    }

    public String getRole(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("com.portodoitaqui.secapi")
                .build().verify(token)
                .getClaim("role").asString();
    }

    public Date getExpiracao(String token) { //não lança exception TokenExpirationException
        return JWT.decode(token)
                .getExpiresAt();
    }

    public boolean isExpired(String token) { //não lança exception TokenExpirationException
        Date currentDate = new Date();
        return JWT.decode(token)
                .getExpiresAt()
                .before(currentDate);
    }


}
