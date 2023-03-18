package edu.bbte.idde.nkim2061.spring.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.nkim2061.spring.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Profile("prod")
public class ConnectionPool {

    @Autowired
    private HikariDataSource dataSource;

    /**
     * @throws RepositoryException
     * dummy description
     */
    public synchronized Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            log.debug("Giving out connection from pool");
            return connection;
        } catch (SQLException e) {
            log.error("Could not get connection from pool", e);
            throw new RepositoryException("Could not get connection from pool", e);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        log.debug("Connection returned to pool");
        dataSource.evictConnection(connection);
    }
}
