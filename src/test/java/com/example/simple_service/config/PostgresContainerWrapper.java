package com.example.simple_service.config;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
@Slf4j
public class PostgresContainerWrapper extends PostgreSQLContainer<PostgresContainerWrapper> {
    private static final String POSTGRES_IMAGE_NAME = "postgres:16";
    private static final String POSTGRES_DB = "micros_test_db";
    private static final String POSTGRES_USER = "micro_test_usr";
    private static final String POSTGRES_PASSWORD = "micro_test_pwd";

    public PostgresContainerWrapper() {
        super(POSTGRES_IMAGE_NAME);
        this

                .withLogConsumer(new Slf4jLogConsumer(log))
                .withDatabaseName(POSTGRES_DB)
                .withUsername(POSTGRES_USER)
                .withPassword(POSTGRES_PASSWORD)
                .withInitScript("init-test.sql");
    }

    @Override
    public void start() {
        super.start();
        this.getContainerId();
    }
}
