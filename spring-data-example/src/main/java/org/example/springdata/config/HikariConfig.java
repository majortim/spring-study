package org.example.springdata.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Driver;

@PropertySource("classpath:/database/h2/jdbc.properties")
@Configuration
public class HikariConfig {
    @Bean
    public DataSourceFactory dataSourceFactory(Environment environment){
        DataSourceFactory dataSourceFactory = new DataSourceFactory() {
            @Override
            public ConnectionProperties getConnectionProperties() {
                ConnectionProperties connectionProperties = new ConnectionProperties() {
                    @Override
                    public void setDriverClass(Class<? extends Driver> aClass) {
                        try {
                            setDriverClass((Class<? extends Driver>) Class.forName(environment.getProperty("jdbc.driverClassName")));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void setUrl(String s) {
                        setUrl(environment.getProperty("jdbc.url"));
                    }

                    @Override
                    public void setUsername(String s) {

                    }

                    @Override
                    public void setPassword(String s) {

                    }
                };

                return connectionProperties;
            }

            @Override
            public DataSource getDataSource() {
                HikariDataSource ds = new HikariDataSource();
                ds.setJdbcUrl(environment.getProperty("jdbc.url"));
                ds.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
                return ds;
            }
        };

        return dataSourceFactory;
    }
}
