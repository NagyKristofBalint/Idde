package edu.bbte.idde.nkim2061.server.repository.jdbc;

import edu.bbte.idde.nkim2061.server.repository.DAOFactory;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAdDAO;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAgentDAO;

public class JdbcDAOFactory extends DAOFactory {

    private static RealEstateAdJdbcDAO realEstateAdJdbcDAO;
    private static RealEstateAgentJdbcDAO realEstateAgentJdbcDAO;

    @Override
    public synchronized RealEstateAdDAO getRealEstateAdDAO() {
        if (realEstateAdJdbcDAO == null) {
            realEstateAdJdbcDAO = new RealEstateAdJdbcDAO();
        }
        return realEstateAdJdbcDAO;
    }

    @Override
    public synchronized RealEstateAgentDAO getRealEstateAgentDAO() {
        if (realEstateAgentJdbcDAO == null) {
            realEstateAgentJdbcDAO = new RealEstateAgentJdbcDAO();
        }
        return realEstateAgentJdbcDAO;
    }
}
