package com.ssk.db.clickhouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

@Configuration
public class ClickhouseDatasourceInitializer {

    @Autowired
    private ClickhouseDBParams dbParams;

    @Bean
    public ClickHouseDataSource dataSource() {
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser("default");
        return new ClickHouseDataSource("jdbc:clickhouse://127.0.0.1:8123", properties);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(ClickHouseDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}