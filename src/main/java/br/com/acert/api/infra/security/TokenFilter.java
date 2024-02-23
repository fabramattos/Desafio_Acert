package br.com.acert.api.infra.security;

import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Caso request tenha token válido, recupera o usuário e evita passar pelo login
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = recuperaToken(request);
        if(token != null) {
            var subject = tokenService.getSubject(token);
            var usuario = repository.findByLogin(subject).orElseThrow();
            var tokenAuthentication = new UsernamePasswordAuthenticationToken(usuario, null, null);

            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperaToken(HttpServletRequest req) {
        var authHeader = req.getHeader("Authorization");
        if(authHeader != null)
            return authHeader.replace("Bearer ", "");
        return null;
    }
}
