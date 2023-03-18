package edu.bbte.idde.nkim2061.spring.service.jpa;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAdDAO;
import edu.bbte.idde.nkim2061.spring.service.ServiceException;
import edu.bbte.idde.nkim2061.spring.service.impl.ImplRealEstateAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
@Profile("jpa")
public class RealEstateAgentJPAService extends ImplRealEstateAgentService {

    @Autowired
    private RealEstateAdDAO realEstateAdDAO;

    @Override
    public Collection<RealEstateAd> getAdvertisementsOfAgent(Long id) {
        try {
            RealEstateAgent agent = realEstateAgentDAO.findById(id).orElse(null);
            if (agent != null) {
                return agent.getRealEstateAds();
            }
            return Collections.emptyList();
        } catch (PersistenceException e) {
            log.error("Error at getting advertisements of agent with id {}", id);
            throw new ServiceException("Error at getting advertisements of agent with id " + id);
        }
    }

    @Override
    public void addAdvertisementToAgent(Long agentId, RealEstateAd advertisement) {
        try {
            realEstateAgentDAO.findById(agentId)
                    .ifPresent(agent -> {
                        agent.getRealEstateAds().add(advertisement);
                        advertisement.setRealEstateAgent(agent);
                        realEstateAgentDAO.save(agent);
                    });
        } catch (PersistenceException e) {
            log.error("Error at adding advertisement to agent with id {}", agentId);
            throw new ServiceException("Error at adding advertisement to agent with id " + agentId);
        }
    }

    @Override
    public void deleteAdvertisement(Long agentId, Long adId) {
        try {
            realEstateAgentDAO.findById(agentId)
                    .ifPresent(agent -> {
                        agent.getRealEstateAds()
                                .removeIf(advert -> advert.getId().equals(adId));
                        realEstateAdDAO.deleteById(adId);
                        realEstateAgentDAO.save(agent);
                    });
        } catch (PersistenceException e) {
            log.error("Error at deleting advertisement with id {} from agent with id {}", adId, agentId);
            throw new ServiceException("Error at deleting advertisement"
                    + "with id " + adId + " from agent with id " + agentId);
        }
    }
}
