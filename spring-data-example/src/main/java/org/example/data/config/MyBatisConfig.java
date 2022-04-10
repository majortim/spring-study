package org.example.data.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.mybatis.MyBatisDataAccessStrategy;
import org.springframework.data.jdbc.mybatis.NamespaceStrategy;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import javax.sql.DataSource;

@Profile("mybatis")
@Configuration
@MapperScan("org.example.data.mapper")
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:/mybatis/mybatis-config.xml"));

        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory)  {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    DataAccessStrategy dataAccessStrategy(SqlSession session, NamedParameterJdbcOperations operations, JdbcConverter jdbcConverter, JdbcMappingContext context, Dialect dialect) {

        NamespaceStrategy namespaceStrategy = new NamespaceStrategy() {
            @Override
            public String getNamespace(Class<?> domainType) {
                return "org.example.data.mapper." + domainType.getSimpleName() + "Mapper";
            }
        };

        return MyBatisDataAccessStrategy.createCombinedAccessStrategy(context, jdbcConverter, operations, session, namespaceStrategy, dialect);
    }
}
