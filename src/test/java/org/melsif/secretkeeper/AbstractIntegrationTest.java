package org.melsif.secretkeeper;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    public static final String POSTGRES_IMAGE_NAME = "postgres:9.6.12";

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME);

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgres)).join();
        }

        private static Map<String, Object> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", postgres.getJdbcUrl(),
                    "spring.datasource.username", postgres.getUsername(),
                    "spring.datasource.password", postgres.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testcontainers = new MapPropertySource("testcontainers", createConnectionConfiguration());
            environment.getPropertySources().addFirst(testcontainers);
        }
    }

    @BeforeClass
    public static void setup() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }
}
