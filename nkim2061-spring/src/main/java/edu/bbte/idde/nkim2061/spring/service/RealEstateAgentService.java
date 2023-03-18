package edu.bbte.idde.nkim2061.spring.service;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;

import java.util.Collection;

public interface RealEstateAgentService extends Service<RealEstateAgent> {

    Collection<RealEstateAd> getAdvertisementsOfAgent(Long id);

    void addAdvertisementToAgent(Long agentId, RealEstateAd advertisement);

    void deleteAdvertisement(Long agentId, Long adId);
}
