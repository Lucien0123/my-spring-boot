package com.lucien.myspringboot.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author huoershuai
 * @Date 2020/5/12
 */
@Configuration
@MapperScan(
        annotationClass = Mapper.class,
        basePackages = "com.lucien.myspringboot.dao",
        sqlSessionFactoryRef = "localSqlSessionFactory"
)
@Data
@Slf4j
public class DBConfiguration extends AbstractDBConfiguration {

    @Value("${mysql.local.database}")
    private String database;

    @Value("${mysql.local.host}")
    private String host;

    @Value("${mysql.local.port}")
    private int port;

    @Value("${mysql.local.username}")
    private String username;

    @Value("${mysql.local.password}")
    private String password;


    @Override
    protected String getDatabase() {
        return database;
    }

    @Override
    protected String getHost() {
        return host;
    }

    @Bean("localDataSource")
    @Override
    public DataSource dataSource() throws SQLException {
        return super.dataSource();
    }

    @Bean("localSqlSessionFactory")
    @Primary
    @Override
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(this.dataSource());
        factory.setVfs(SpringBootVFS.class);
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(patternResolver.getResources("classpath:mappers/local/*.xml"));
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(config);
        return factory.getObject();
    }

    @Bean
    @Primary
    @Override
    public DataSourceTransactionManager transactionManager() throws SQLException {
        return super.transactionManager();
    }


}
