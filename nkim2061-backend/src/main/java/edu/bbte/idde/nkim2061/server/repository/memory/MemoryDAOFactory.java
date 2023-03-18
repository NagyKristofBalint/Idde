package edu.bbte.idde.nkim2061.server.repository.memory;

import edu.bbte.idde.nkim2061.server.repository.DAOFactory;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAdDAO;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAgentDAO;

public class MemoryDAOFactory extends DAOFactory {

    private static RealEstateAdMemoryDAO realEstateAdMemoryDAO;
    private static RealEstateAgentMemoryDAO realEstateAgentMemoryDAO;

    @Override
    public synchronized RealEstateAdDAO getRealEstateAdDAO() {
        if (realEstateAdMemoryDAO == null) {
            realEstateAdMemoryDAO = new RealEstateAdMemoryDAO();
        }
        return realEstateAdMemoryDAO;
    }

    @Override
    public synchronized RealEstateAgentDAO getRealEstateAgentDAO() {
        if (realEstateAgentMemoryDAO == null) {
            realEstateAgentMemoryDAO = new RealEstateAgentMemoryDAO();
        }
        return realEstateAgentMemoryDAO;
    }
}
