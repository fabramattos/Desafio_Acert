package br.com.acert.api.infra.security;

import br.com.acert.api.domain.cliente.ClienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Caso request tenha token válido, recupera o usuário e evita passar pelo login
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ClienteRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = tokenUtils.recuperaToken(request);
        if(token != null) {
            var subject = tokenUtils.getSubject(token);
            var usuario = repository.findByLogin(subject).orElseThrow();
            var tokenAuthentication = new UsernamePasswordAuthenticationToken(usuario, null, null);

            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        filterChain.doFilter(request, response);
    }


}
