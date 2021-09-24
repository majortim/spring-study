package org.example.webmvc.config.root;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(){

        HikariConfig hikariConfig = new HikariConfig("/database/hsqldb/hikari.properties");

        DataSource ds = new HikariDataSource(hikariConfig);

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/database/hsqldb/schema.sql"));
        resourceDatabasePopulator.addScripts(new ClassPathResource("/database/hsqldb/pet.sql"));
        DatabasePopulatorUtils.execute(resourceDatabasePopulator, ds);

        return ds;
    }
}
