package edu.bbte.idde.nkim2061.server.service.impl;

import edu.bbte.idde.nkim2061.server.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.server.repository.DAOFactory;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAgentDAO;
import edu.bbte.idde.nkim2061.server.repository.RepositoryException;
import edu.bbte.idde.nkim2061.server.service.RealEstateAgentService;
import edu.bbte.idde.nkim2061.server.service.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class ImplRealEstateAgentService implements RealEstateAgentService {
    private final RealEstateAgentDAO realEstateAgentDAO;

    public ImplRealEstateAgentService() {
        realEstateAgentDAO = DAOFactory.getInstance().getRealEstateAgentDAO();
    }

    @Override
    public RealEstateAgent save(RealEstateAgent realEstateAgent) {
        try {
            return realEstateAgentDAO.create(realEstateAgent);
        } catch (RepositoryException e) {
            log.error("Error while saving realEstateAgent: " + realEstateAgent, e);
            throw new ServiceException("Error while saving realEstateAgent", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            realEstateAgentDAO.deleteById(id);
        } catch (RepositoryException e) {
            log.error("Error while deleting realEstateAgent with id: " + id, e);
            throw new ServiceException("Error while deleting realEstateAgent", e);
        }
    }

    @Override
    public RealEstateAgent findById(Long id) {
        try {
            return realEstateAgentDAO.getById(id);
        } catch (RepositoryException e) {
            log.error("Error while finding realEstateAgent with id: " + id, e);
            throw new ServiceException("Error while finding realEstateAgent", e);
        }
    }

    @Override
    public Collection<RealEstateAgent> findAll() {
        try {
            return realEstateAgentDAO.getAll();
        } catch (RepositoryException e) {
            log.error("Error while finding all realEstateAgents", e);
            throw new ServiceException("Error while finding all realEstateAgents", e);
        }
    }

    @Override
    public void update(RealEstateAgent realEstateAgent) {
        try {
            if (realEstateAgentDAO.getById(realEstateAgent.getId()) == null) {
                log.warn("Cannot update real estate agent with id {}, it does not exist", realEstateAgent.getId());
            } else {
                realEstateAgentDAO.update(realEstateAgent);
            }
        } catch (RepositoryException e) {
            log.error("Error while updating realEstateAd", e);
            throw new ServiceException("Error while updating realEstateAd", e);
        }
    }
}
