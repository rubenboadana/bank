package com.iobuilders;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.spring.CucumberContextConfiguration;
import io.holixon.axon.testcontainer.AxonServerContainer;
import io.holixon.axon.testcontainer.spring.AxonServerContainerSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CucumberSpringConfig {

    private static final String POSTGRES_DB = "example";
    private static final String POSTGRES_USER = "1234";
    private static final String POSTGRES_PASSWORD = "example";
    private static final DockerImageName postgres = DockerImageName
            .parse("postgres:13.1-alpine")
            .asCompatibleSubstituteFor("postgres");

    public static final AxonServerContainer AXON = AxonServerContainer.builder()
            .enableDevMode()
            .build();

    private static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>(postgres)
            .withDatabaseName(POSTGRES_DB)
            .withUsername(POSTGRES_USER)
            .withPassword(POSTGRES_PASSWORD);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setup() {
        DATABASE.start();
        AXON.start();
    }

    @After
    public void cleanupDatabase() {
        jdbcTemplate.queryForList("select 'truncate table \"' || tablename || '\"restart identity cascade;' from pg_tables where schemaname='public';\n", String.class)
                .forEach(jdbcTemplate::update);
    }

    @AfterAll
    public static void tearDown() {
        DATABASE.stop();
        AXON.stop();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE::getJdbcUrl);
        registry.add("spring.datasource.password", DATABASE::getPassword);
        registry.add("spring.datasource.username", DATABASE::getUsername);
        AxonServerContainerSpring.addDynamicProperties(AXON, registry);
    }

}
