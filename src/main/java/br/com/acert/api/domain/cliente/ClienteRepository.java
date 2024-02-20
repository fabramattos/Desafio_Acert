package br.com.acert.api.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByLogin(String login);
}
