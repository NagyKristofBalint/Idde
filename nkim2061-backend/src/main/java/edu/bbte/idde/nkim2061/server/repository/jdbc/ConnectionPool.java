package edu.bbte.idde.nkim2061.server.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.nkim2061.server.config.ConfigFactory;
import edu.bbte.idde.nkim2061.server.config.JdbcConfiguration;
import edu.bbte.idde.nkim2061.server.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public final class ConnectionPool {

    private static ConnectionPool instance;
    private static final HikariDataSource DATA_SOURCE = new HikariDataSource();

    private ConnectionPool() {
        JdbcConfiguration config = ConfigFactory.getJdbcConfiguration();
        DATA_SOURCE.setDriverClassName(config.getDriverClass());
        DATA_SOURCE.setJdbcUrl(config.getUrl());
        DATA_SOURCE.setUsername(config.getUser());
        DATA_SOURCE.setPassword(config.getPassword());
        DATA_SOURCE.setMaximumPoolSize(config.getPoolSize());
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * @throws RepositoryException
     * dummy description
     */
    public synchronized Connection getConnection() {
        try {
            Connection connection = DATA_SOURCE.getConnection();
            log.info("Giving out connection from pool");
            return connection;
        } catch (SQLException e) {
            log.error("Could not get connection from pool", e);
            throw new RepositoryException("Could not get connection from pool", e);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        DATA_SOURCE.evictConnection(connection);
    }
}
