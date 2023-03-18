package edu.bbte.idde.nkim2061.server.repository.jdbc;

import edu.bbte.idde.nkim2061.server.config.ConfigFactory;
import edu.bbte.idde.nkim2061.server.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAgentDAO;
import edu.bbte.idde.nkim2061.server.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

@Slf4j
public final class RealEstateAgentJdbcDAO implements RealEstateAgentDAO {

    private final ConnectionPool connectionPool;

    public RealEstateAgentJdbcDAO() {
        connectionPool = ConnectionPool.getInstance();
        if (ConfigFactory.getJdbcConfiguration().isCreateTables()) {
            Connection connection = null;
            try {
                connection = connectionPool.getConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS real_estate_agents ("
                                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                                + "first_name VARCHAR(255),"
                                + "last_name VARCHAR(255),"
                                + "email VARCHAR(255),"
                                + "phone VARCHAR(255),"
                                + "age INT"
                                + ");");
                log.info("Table real_estate_agents created(if didn't exist) with success");
            } catch (SQLException e) {
                log.error("SQL command failed", e);
                throw new RepositoryException("SQL command failed", e);
            } catch (RepositoryException e) {
                log.error("Connection to DB failed", e);
                throw new RepositoryException("Connection to DB failed", e);
            } finally {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public RealEstateAgent create(RealEstateAgent realEstateAgent) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "INSERT INTO real_estate_agents"
                    + "(first_name, last_name, email, phone, age) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            fillPreparedStatement(realEstateAgent, preparedStatement);
            preparedStatement.executeUpdate();

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                realEstateAgent.setId(id);
                log.info("Real estate agent has been inserted with generated id: {}", id);
            } else {
                log.warn("Real estate agent with email {} has not been inserted,"
                        + "but no id is retrieved", realEstateAgent.getEmail());
            }
            return realEstateAgent;
        } catch (SQLException e) {
            log.error("SQL command failed", e);
            throw new RepositoryException("SQL command failed", e);
        } catch (RepositoryException e) {
            log.error("Connection to DB failed", e);
            throw new RepositoryException("Connection to DB failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "DELETE FROM real_estate_agents WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            log.info("Real estate agent with id {} has been deleted", id);
        } catch (SQLException e) {
            log.error("SQL command failed", e);
            throw new RepositoryException("SQL command failed", e);
        } catch (RepositoryException e) {
            log.error("Connection to DB failed", e);
            throw new RepositoryException("Connection to DB failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public RealEstateAgent getById(Long id) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM real_estate_agents WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                RealEstateAgent realEstateAgent = resultSetToRealEstateAgent(resultSet);
                log.info("Real estate agent with id {} has been retrieved", id);
                return realEstateAgent;
            } else {
                log.warn("Real estate agent with id {} was not found", id);
                return null;
            }
        } catch (SQLException e) {
            log.error("SQL command failed", e);
            throw new RepositoryException("SQL command failed", e);
        } catch (RepositoryException e) {
            log.error("Connection to DB failed", e);
            throw new RepositoryException("Connection to DB failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Collection<RealEstateAgent> getAll() {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM real_estate_agents";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet results = preparedStatement.executeQuery();
            Collection<RealEstateAgent> realEstateAgents = new LinkedList<>();
            while (results.next()) {
                RealEstateAgent agent = resultSetToRealEstateAgent(results);
                realEstateAgents.add(agent);
            }
            log.info("All real estate agents have been retrieved");
            return realEstateAgents;
        } catch (SQLException e) {
            log.error("SQL command failed", e);
            throw new RepositoryException("SQL command failed", e);
        } catch (RepositoryException e) {
            log.error("Connection to DB failed", e);
            throw new RepositoryException("Connection to DB failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(RealEstateAgent realEstateAgent) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "UPDATE real_estate_agents SET first_name = ?,"
                    + "last_name = ?,"
                    + "email = ?,"
                    + "phone = ?,"
                    + "age = ?"
                    + "WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            fillPreparedStatement(realEstateAgent, preparedStatement);
            preparedStatement.setLong(6, realEstateAgent.getId());

            preparedStatement.executeUpdate();
            log.info("Real estate agent with id {} has been updated", realEstateAgent.getId());
        } catch (SQLException e) {
            log.error("SQL command failed", e);
            throw new RepositoryException("SQL command failed", e);
        } catch (RepositoryException e) {
            log.error("Connection to DB failed", e);
            throw new RepositoryException("Connection to DB failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public RealEstateAgent getByEmail(String email) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM real_estate_agents WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                RealEstateAgent agent = resultSetToRealEstateAgent(resultSet);
                log.info("Real estate agent with email {} has been retrieved", email);
                return agent;
            } else {
                log.warn("Real estate agent with email {} was not found", email);
                return null;
            }
        } catch (SQLException e) {
            log.error("SQL command failed", e);
            throw new RepositoryException("SQL command failed", e);
        } catch (RepositoryException e) {
            log.error("Connection to DB failed", e);
            throw new RepositoryException("Connection to DB failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void fillPreparedStatement(RealEstateAgent realEstateAgent,
                                       PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, realEstateAgent.getFirstName());
        preparedStatement.setString(2, realEstateAgent.getLastName());
        preparedStatement.setString(3, realEstateAgent.getEmail());
        preparedStatement.setString(4, realEstateAgent.getPhone());
        preparedStatement.setInt(5, realEstateAgent.getAge());
    }

    private RealEstateAgent resultSetToRealEstateAgent(ResultSet resultSet) throws SQLException {
        RealEstateAgent realEstateAgent = new RealEstateAgent();
        realEstateAgent.setId(resultSet.getLong("id"));
        realEstateAgent.setFirstName(resultSet.getString("first_name"));
        realEstateAgent.setLastName(resultSet.getString("last_name"));
        realEstateAgent.setEmail(resultSet.getString("email"));
        realEstateAgent.setPhone(resultSet.getString("phone"));
        realEstateAgent.setAge(resultSet.getInt("age"));
        return realEstateAgent;
    }
}

