package com.ust.sdet.tests;

import com.ust.sdet.config.DatabaseConfig;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.repository.OrderRepository;
import com.ust.sdet.support.DbSupport;
import com.ust.sdet.support.TestEnvironment;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractOrderTest {

    static MySQLContainer<?> mysql;
    static DatabaseConfig config;
    static OrderRepository repository;
    static OrderFactory factory;
    static DbSupport dbSupport;

    @BeforeAll
    static void setupDatabase() {
        boolean useContainer = Boolean.parseBoolean(TestEnvironment.optional("USE_TESTCONTAINERS", "false"));

        if (useContainer) {
            mysql = new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("orders_db")
                    .withUsername("test")
                    .withPassword("test");
            mysql.start();

            config = new DatabaseConfig(
                    mysql.getJdbcUrl() + "?allowPublicKeyRetrieval=true&useSSL=false",
                    mysql.getUsername(),
                    mysql.getPassword()
            );
        } else {
            config = DatabaseConfig.fromEnvironmentCredential();
        }

        Flyway.configure()
                .dataSource(config.jdbcUrl(), config.username(), config.password())
                .locations("classpath:db/migration")
                .load()
                .migrate();

        repository = new OrderRepository(config);
        factory = new OrderFactory(repository);
        dbSupport = new DbSupport(config);
    }

    @AfterAll
    static void tearDownDatabase() {
        if (mysql != null) {
            mysql.stop();
        }
    }

    @BeforeEach
    void resetDatabase() {
        repository.reset();
    }
}