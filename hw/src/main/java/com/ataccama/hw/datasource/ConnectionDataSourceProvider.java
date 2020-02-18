package com.ataccama.hw.datasource;

import com.ataccama.hw.model.Connection;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class ConnectionDataSourceProvider {

    private final static String DB_BASE_URL = "jdbc:postgresql://";

    public JdbcTemplate getJdbcTemplate(final Connection connection) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(buildUrl(connection));
        dataSourceBuilder.username(connection.getUsername());
        dataSourceBuilder.password(connection.getPassword());
        return new JdbcTemplate(dataSourceBuilder.build());
    }

    private String buildUrl(final Connection connection) {
        return DB_BASE_URL + connection.getHostname() + ":" + connection.getPort() + "/" + connection.getDatabaseName();
    }
}
