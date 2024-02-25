package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.infra.exception.TokenGeradoException;
import br.com.acert.api.infra.exception.TokenInvalidoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

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

    private Instant diasValidade(Long dias) {
        var duration = Duration.ofDays(dias).toSeconds();
        return Instant.now().plusSeconds(duration);
    }

    public Long idUsuarioAutenticado(){
        return (Long) ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest()
                .getAttribute("userId");
    }

    public void comparaComUserIdAutenticado(Long idParaVerificar, RuntimeException e){
        if(!idUsuarioAutenticado().equals(idParaVerificar))
            throw e;
    }
}
