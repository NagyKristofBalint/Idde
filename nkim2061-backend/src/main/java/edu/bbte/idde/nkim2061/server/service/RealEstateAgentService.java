package edu.bbte.idde.nkim2061.server.service;

import edu.bbte.idde.nkim2061.server.model.RealEstateAgent;

import java.util.Collection;

public interface RealEstateAgentService extends Service<RealEstateAgent> {

    @Override
    RealEstateAgent save(RealEstateAgent realEstateAgent);

    @Override
    void deleteById(Long id);

    @Override
    RealEstateAgent findById(Long id);

    @Override
    Collection<RealEstateAgent> findAll();

    @Override
    void update(RealEstateAgent realEstateAgent);
}
