package edu.bbte.idde.nkim2061.server.repository;

import edu.bbte.idde.nkim2061.server.model.RealEstateAgent;

public interface RealEstateAgentDAO extends DAO<RealEstateAgent> {
    RealEstateAgent getByEmail(String email);
}
