package org.example.springdata.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Profile("embedded")
@Configuration
public class EmbeddedDatabaseConfig extends AbstractJdbcConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(name = "embeddedDataSource")
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("classpath:/database/h2/schema.sql")
                .build();
    }

    @Profile("hikari")
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        DataSource dataSource = embeddedDataSource();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(dataSource);
        hikariConfig.setJdbcUrl("jdbc:h2:~/h2db;MODE=MySQL;DATABASE_TO_LOWER=TRUE");
        return new HikariDataSource(hikariConfig);
    }
}
