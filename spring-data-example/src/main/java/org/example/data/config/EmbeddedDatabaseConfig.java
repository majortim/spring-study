package org.example.data.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

//@Profile("embedded")
@Configuration
public class EmbeddedDatabaseConfig {

    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("classpath:/database/h2/schema.sql")
                .addScript("classpath:/database/h2/data.sql")
                .build();
    }

//    @Profile("hikari")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSource dataSource = embeddedDataSource();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(dataSource);
        hikariConfig.setJdbcUrl("jdbc:h2:mem:h2db;MODE=MySQL;DATABASE_TO_LOWER=TRUE");
        return new HikariDataSource(hikariConfig);
    }
}
