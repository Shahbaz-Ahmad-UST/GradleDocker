package com.ust.sdet.tests;

import com.ust.sdet.config.DatabaseConfig;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.repository.OrderRepository;
import com.ust.sdet.support.DbSupport;
import com.ust.sdet.support.TestEnvironment;
import io.cucumber.java.ja.且つ;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractOrderTest {

    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("orders_db")
            .withUsername(TestEnvironment.required("DB_USER"))
            .withPassword(TestEnvironment.required("DB_PASSWORD"));

    static DatabaseConfig config;
    static OrderRepository repository;
    static OrderFactory factory;
    static DbSupport dbSupport;


    @BeforeAll
    static void setupDatabase() {

        String useContainer = TestEnvironment.required("USE_TESTCONTAINERS");

        if ("true".equalsIgnoreCase(useContainer)) {

            MYSQL.start();

            config = DatabaseConfig.fromContainer(
                    MYSQL.getJdbcUrl() + "?allowPublicKeyRetrieval=true&useSSL=false",
                    MYSQL.getUsername(),
                    MYSQL.getPassword()
            );
        } else {

            config = new DatabaseConfig(
                    TestEnvironment.required("DB_JDBC_URL"),
                    TestEnvironment.required("DB_USER"),
                    TestEnvironment.required("DB_PASSWORD")
            );
        }

        Flyway flyway = Flyway.configure()
                .dataSource(config.jdbcUrl(), config.username(), config.password())
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        repository = new OrderRepository(config);
        factory = new OrderFactory(repository);
        dbSupport = new DbSupport(config);
    }


    @BeforeEach
    void resetDatabase() {
        repository.reset();
    }
}