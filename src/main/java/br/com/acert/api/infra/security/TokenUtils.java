package br.com.acert.api.infra.security;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.infra.exception.TokenGeradoException;
import br.com.acert.api.infra.exception.TokenInvalidoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class TokenUtils {

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
                    .withClaim("userId", cliente.getId())
                    .withExpiresAt(diasValidade(DIAS_EXPIRACAO))
                    .sign(algoritmo);
        } catch (JWTCreationException e) {
            throw new TokenGeradoException();
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
            throw new TokenInvalidoException();
        }
    }

    public Long getUserId(HttpServletRequest req) {
        var token = recuperaToken(req);
        var algoritmo = Algorithm.HMAC512(JWT_SECRET);
        try {
            return JWT
                    .require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getClaim("userId")
                    .asLong();
        } catch (JWTVerificationException e) {
            throw new TokenInvalidoException();
        }
    }

    private Instant diasValidade(Long dias) {
        var duration = Duration.ofDays(dias).toSeconds();
        return Instant.now().plusSeconds(duration);
    }

    protected String recuperaToken(HttpServletRequest req) {
        var authHeader = req.getHeader("Authorization");
        if(authHeader != null)
            return authHeader.replace("Bearer ", "");
        return null;
    }
}
