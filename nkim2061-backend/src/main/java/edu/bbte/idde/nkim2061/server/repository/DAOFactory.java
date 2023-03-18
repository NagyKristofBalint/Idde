package edu.bbte.idde.nkim2061.server.repository;

import edu.bbte.idde.nkim2061.server.config.ConfigFactory;
import edu.bbte.idde.nkim2061.server.repository.jdbc.JdbcDAOFactory;
import edu.bbte.idde.nkim2061.server.repository.memory.MemoryDAOFactory;

public abstract class DAOFactory {

    private static DAOFactory instance;

    protected DAOFactory() {
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            if ("jdbc".equals(ConfigFactory.getProfile())) {
                instance = new JdbcDAOFactory();
            } else {
                instance = new MemoryDAOFactory();
            }
        }
        return instance;
    }

    public abstract RealEstateAdDAO getRealEstateAdDAO();

    public abstract RealEstateAgentDAO getRealEstateAgentDAO();
}
