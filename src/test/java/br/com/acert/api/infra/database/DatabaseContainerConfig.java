package br.com.acert.api.infra.database;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;


@ActiveProfiles("test")
@Transactional
@SpringBootTest
public abstract class DatabaseContainerConfig {

    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withReuse(true);

    static {
        postgresqlContainer.start();
    }
}
