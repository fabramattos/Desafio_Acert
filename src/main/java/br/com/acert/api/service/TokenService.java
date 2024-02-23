package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.Cliente;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private final String ISSUER = "acert_api";


    public String geraToken(Cliente cliente) {
        var algoritmo = Algorithm.HMAC512(JWT_SECRET);
        try {
            Long DIAS_EXPIRACAO = 1L;
            return JWT
                    .create()
                    .withIssuer(ISSUER)
                    .withSubject(cliente.getLogin())
                    .withExpiresAt(diasValidade(DIAS_EXPIRACAO))
                    .sign(algoritmo);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar jwt");
        }
    }

    public String getSubject(String token) {
        var algoritmo = Algorithm.HMAC512(JWT_SECRET);
        try {
            return JWT
                    .require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("JWT inválido ou expirado");
        }
    }

    private Instant diasValidade(Long dias) {
        var duration = Duration.ofDays(dias).toSeconds();
        return Instant.now().plusSeconds(duration);
    }

}
