package com.gpbapp.metadataregistry.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Настройки для оптимизации больших запросов
        jdbcTemplate.setFetchSize(1000); // Размер пачки для потоковой обработки
        jdbcTemplate.setQueryTimeout(30); // Таймаут 30 секунд

        return jdbcTemplate;
    }
}
