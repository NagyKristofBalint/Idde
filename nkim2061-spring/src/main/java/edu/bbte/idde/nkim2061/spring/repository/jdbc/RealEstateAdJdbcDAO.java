package edu.bbte.idde.nkim2061.spring.repository.jdbc;

import edu.bbte.idde.nkim2061.spring.config.JdbcConfiguration;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAdDAO;
import edu.bbte.idde.nkim2061.spring.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Slf4j
@Repository
@Profile("prod")
public class RealEstateAdJdbcDAO implements RealEstateAdDAO {

    @Autowired
    private ConnectionPool connectionPool;

    @Autowired
    private JdbcConfiguration jdbcConfiguration;

    @PostConstruct
    public void postConstruct() {
        if (jdbcConfiguration.isCreateTables()) {
            Connection connection = null;
            try {
                connection = connectionPool.getConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS real_estate_ads ("
                                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                                + "ad_date DATE,"
                                + "description VARCHAR(255),"
                                + "address VARCHAR(255),"
                                + "city VARCHAR(255),"
                                + "price REAL,"
                                + "real_estate_agent_id BIGINT,"
                                + "CONSTRAINT FK_Ad_Agent FOREIGN KEY (id) "
                                + "REFERENCES real_estate_agents(id)"
                                + ");");
                log.info("Table real_estate_ads created(if didn't exist) with success");
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
    public RealEstateAd save(RealEstateAd realEstateAd) {
        if (realEstateAd.getId() == null) {
            Connection connection = null;
            try {
                connection = connectionPool.getConnection();
                String query = "INSERT INTO real_estate_ads"
                        + "(ad_date, description, address, city, price) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement prepStmt = connection.prepareStatement(query);
                fillPreparedStatement(realEstateAd, prepStmt);
                prepStmt.executeUpdate();

                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    realEstateAd.setId(id);
                    log.info("Real estate ad has been inserted with generated id: {}", id);
                } else {
                    log.warn("Real estate ad with date {} has been inserted but no id is retrieved",
                            realEstateAd.getDate());
                }
            } catch (SQLException | RepositoryException e) {
                log.error("Error creating entity", e);
                throw new RepositoryException("Error creating entity", e);
            } finally {
                connectionPool.returnConnection(connection);
            }
        } else {
            update(realEstateAd);
        }
        return realEstateAd;
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "DELETE FROM real_estate_ads WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            log.info("Real estate ad with id {} has been deleted with success", id);
        } catch (SQLException | RepositoryException e) {
            log.error("Error deleting entity with id {}", id, e);
            throw new RepositoryException("Error deleting entity with id " + id, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Optional<RealEstateAd> findById(Long id) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM real_estate_ads WHERE id = ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                log.warn("No real estate ad with id {} found", id);
                return Optional.empty();
            }
            RealEstateAd advertisement = resultSetToRealEstateAd(result);

            log.info("Real estate ad with id {} has been queried with success", id);
            return Optional.of(advertisement);
        } catch (SQLException | RepositoryException e) {
            log.error("Getting RealEstateAd by id {} failed", id, e);
            throw new RepositoryException("Getting RealEstateAd by id " + id + " failed", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Collection<RealEstateAd> findAll() {
        Connection connection = null;
        Collection<RealEstateAd> list = new LinkedList<>();
        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM real_estate_ads";

            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                RealEstateAd advertisement = resultSetToRealEstateAd(result);
                list.add(advertisement);
            }

            log.info("All RealEstateAds have been queried with success");
            return list;
        } catch (SQLException | RepositoryException e) {
            log.error("Error at getting all RealEstateAds", e);
            throw new RepositoryException("Error at getting all RealEstateAds", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void update(RealEstateAd realEstateAd) {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String query = "UPDATE real_estate_ads SET ad_date = ?,"
                    + "description = ?,"
                    + "address = ?,"
                    + "city = ?,"
                    + "price = ?"
                    + "WHERE id = ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            fillPreparedStatement(realEstateAd, stmt);

            stmt.setLong(6, realEstateAd.getId());
            stmt.executeUpdate();

            log.info("Real estate ad with id {}, has been updated with success", realEstateAd.getId());
        } catch (SQLException | RepositoryException e) {
            log.error("Error at updating RealEstateAd with id {}.", realEstateAd.getId(), e);
            throw new RepositoryException("Error at updating RealEstateAd with id " + realEstateAd.getId(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public RealEstateAd getByAddress(String address) {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            String query = "SELECT * FROM real_estate_ads WHERE address = ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, address);
            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            RealEstateAd advertisement = resultSetToRealEstateAd(result);

            log.info("Real estate ad with address {} has been queried with success", address);
            return advertisement;
        } catch (SQLException | RepositoryException e) {
            log.error("Error at getting RealEstateAd with address {}", address, e);
            throw new RepositoryException("Error at getting RealEstateAd with address " + address, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void fillPreparedStatement(RealEstateAd realEstateAd,
                                       PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, realEstateAd.getDate());
        preparedStatement.setString(2, realEstateAd.getDescription());
        preparedStatement.setString(3, realEstateAd.getAddress());
        preparedStatement.setString(4, realEstateAd.getCity());
        preparedStatement.setFloat(5, realEstateAd.getPrice());
    }

    private RealEstateAd resultSetToRealEstateAd(ResultSet result) throws SQLException {
        RealEstateAd advertisement = new RealEstateAd();
        advertisement.setId(result.getLong(1));
        advertisement.setDate(result.getDate(2));
        advertisement.setDescription(result.getString(3));
        advertisement.setAddress(result.getString(4));
        advertisement.setCity(result.getString(5));
        advertisement.setPrice(result.getFloat(6));
        return advertisement;
    }
}
