package com.lucien.myspringboot.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author huoershuai
 * @Date 2020/5/12
 */
@Data
@Slf4j
public abstract class AbstractDBConfiguration {

    @Value("${mysql.username}")
    private String userName;
    @Value("${mysql.password}")
    private String password;
    @Value("${mysql.port}")
    private int port;
    @Value("${mysql.maxActive}")
    private int maxActive;
    @Value("${mysql.minIdle}")
    private int maxMinIdle;
    @Value("${mysql.maxWait}")
    private int maxWait;
    @Value("${mysql.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${mysql.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${mysql.testWhileIdle}")
    private Boolean testWhileIdle;

    protected abstract String getDatabase();

    protected abstract String getHost();

    public DataSource dataSource() throws SQLException {
        return this.dataSource(this.getHost(), this.getPort(), this.getDatabase());
    }

    protected DataSource dataSource(String host, int port, String database) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername(this.getUserName());
        dataSource.setPassword(this.getPassword());
        dataSource.setMaxActive(this.getMaxActive());
        dataSource.setMinIdle(this.getMaxMinIdle());
        dataSource.setMaxWait((long)this.getMaxWait());
        dataSource.setTestWhileIdle(this.getTestWhileIdle().booleanValue());
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTimeBetweenEvictionRunsMillis((long)this.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis((long)this.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery("SELECT 'x'");

        try {
            dataSource.init();
            return dataSource;
        } catch (SQLException var6) {
            log.error("init dataSource(" + dataSource.getUrl() + ") fail", var6);
            throw var6;
        }
    }

    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(this.dataSource());
        factory.setVfs(SpringBootVFS.class);
        Configuration config = new Configuration();
        config.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(config);
        return factory.getObject();
    }

    public DataSourceTransactionManager transactionManager() throws SQLException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource());
        return transactionManager;
    }
}
