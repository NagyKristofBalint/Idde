package edu.bbte.idde.nkim2061.spring.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("prod")
@Configuration
@ConfigurationProperties(prefix = "jdbc")
public class JdbcConfiguration {
    private boolean createTables;
    private String driverClass;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    @Bean
    public HikariDataSource createHikariCP(@Autowired JdbcConfiguration config) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(config.getDriverClass());
        dataSource.setJdbcUrl(config.getUrl());
        dataSource.setUsername(config.getUser());
        dataSource.setPassword(config.getPassword());
        dataSource.setMaximumPoolSize(config.getPoolSize());
        return dataSource;
    }
}
