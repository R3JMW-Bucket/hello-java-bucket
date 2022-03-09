package com.helloj.test.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(TestDataSourceProperties.class)
@MapperScan(basePackages = "com.helloj.mapper", sqlSessionFactoryRef = "testSqlSessionFactory")
@EnableTransactionManagement
public class TestDataSourceConfig {
    private final TestDataSourceProperties testDataSourceProperties;

    public TestDataSourceConfig(TestDataSourceProperties testDataSourceProperties) {
        this.testDataSourceProperties = testDataSourceProperties;
    }

    @Bean(name = "testDataSource")
    @Scope(value = "singleton")
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(testDataSourceProperties.getDriverClassName());
        dataSource.setUsername(testDataSourceProperties.getUsername());
        dataSource.setPassword(testDataSourceProperties.getPassword());
        dataSource.setUrl(testDataSourceProperties.getUrl());
        return dataSource;
    }

    @Bean(name = "testSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/TestMapper.xml"));
        factoryBean.setDataSource(testDataSource());

        return factoryBean.getObject();
    }
}
