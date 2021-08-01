package org.example.springdata.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Profile("mybatis")
public class MyBatisConfig {
    @DependsOn("dataSource")
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:/mybatis/mybatis-configuration.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:/mybatis/mapper/*.xml"));

        return (SqlSessionFactory) sqlSessionFactory.getObject();
    }
}
