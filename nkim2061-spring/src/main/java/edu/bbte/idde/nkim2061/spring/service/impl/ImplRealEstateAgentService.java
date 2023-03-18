package edu.bbte.idde.nkim2061.spring.service.impl;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAgentDAO;
import edu.bbte.idde.nkim2061.spring.repository.RepositoryException;
import edu.bbte.idde.nkim2061.spring.service.RealEstateAgentService;
import edu.bbte.idde.nkim2061.spring.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Collection;

@Slf4j
@Service
@Profile({"dev", "prod"})
public class ImplRealEstateAgentService implements RealEstateAgentService {

    @Autowired
    protected RealEstateAgentDAO realEstateAgentDAO;

    @Override
    public RealEstateAgent save(RealEstateAgent realEstateAgent) {
        try {
            return realEstateAgentDAO.save(realEstateAgent);
        } catch (RepositoryException | PersistenceException e) {
            log.error("Error while saving realEstateAgent: " + realEstateAgent, e);
            throw new ServiceException("Error while saving realEstateAgent", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            realEstateAgentDAO.deleteById(id);
        } catch (RepositoryException | PersistenceException e) {
            log.error("Error while deleting realEstateAgent with id: " + id, e);
            throw new ServiceException("Error while deleting realEstateAgent", e);
        }
    }

    @Override
    public RealEstateAgent findById(Long id) {
        try {
            return realEstateAgentDAO.findById(id).orElse(null);
        } catch (RepositoryException | PersistenceException e) {
            log.error("Error while finding realEstateAgent with id: " + id, e);
            throw new ServiceException("Error while finding realEstateAgent", e);
        }
    }

    @Override
    public Collection<RealEstateAgent> findAll() {
        try {
            return realEstateAgentDAO.findAll();
        } catch (RepositoryException | PersistenceException e) {
            log.error("Error while finding all realEstateAgents", e);
            throw new ServiceException("Error while finding all realEstateAgents", e);
        }
    }

    @Override
    public Collection<RealEstateAd> getAdvertisementsOfAgent(Long id) {
        throw new ServiceException("Unsupported method");
    }

    @Override
    public void addAdvertisementToAgent(Long agentId, RealEstateAd advertisement) {
        throw new ServiceException("Unsupported method");
    }

    @Override
    public void deleteAdvertisement(Long agentId, Long adId) {
        throw new ServiceException("Unsupported method");
    }
}
