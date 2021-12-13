package com.ssk.clickhouse.conn;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

public class ClickhouseDatasourceInitializer {
    public static ClickHouseDataSource createDataSource(ClientConnectionConfig connectionConfig) {
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser(connectionConfig.getUsername());
        properties.setPassword(connectionConfig.getPassword());
        String url = String.format("jdbc:clickhouse://%s:%s", connectionConfig.getHostname(), connectionConfig.getPort());
        return new ClickHouseDataSource(url, properties);
    }
}