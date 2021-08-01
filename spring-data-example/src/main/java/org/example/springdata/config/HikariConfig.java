package org.example.springdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;

@Configuration
public class HikariConfig {
    @Bean
    public DataSourceFactory dataSourceFactory(){
        DataSourceFactory dataSourceFactory = new DataSourceFactory() {
            @Override
            public ConnectionProperties getConnectionProperties() {
                return null;
            }

            @Override
            public DataSource getDataSource() {
                return null;
            }
        };

        return dataSourceFactory;
    }
}
