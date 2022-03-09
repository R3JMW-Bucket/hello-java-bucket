package com.helloj.test;

import com.helloj.HellojApplication;
import com.helloj.mapper.TestMapper;
import com.helloj.test.config.TestDataSourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = {TestDataSourceConfig.class})
@ExtendWith(SpringExtension.class)
@Testcontainers
public class TestMyBatisMagicNumber {

    private static final Logger logger = LoggerFactory.getLogger(TestMyBatisMagicNumber.class);

    private final TestMapper testMapper;

    public TestMyBatisMagicNumber(@Autowired TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:14.1")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeAll
    public static void beforeAll() {
        POSTGRE_SQL_CONTAINER.start();
        System.setProperty("helloj.db.driverClassName", POSTGRE_SQL_CONTAINER.getDriverClassName());
        System.setProperty("helloj.db.username", POSTGRE_SQL_CONTAINER.getUsername());
        System.setProperty("helloj.db.password", POSTGRE_SQL_CONTAINER.getPassword());
        System.setProperty("helloj.db.url", POSTGRE_SQL_CONTAINER.getJdbcUrl());
    }

    @Test
    public void testJavaConstant() {
        String result = this.testMapper.testJavaConstant();
        logger.info(result);
    }

    @AfterAll
    public static void afterAll() {
        POSTGRE_SQL_CONTAINER.stop();
    }
}
