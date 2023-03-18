package edu.bbte.idde.nkim2061.spring.repository;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;

public interface RealEstateAgentDAO extends DAO<RealEstateAgent> {
    RealEstateAgent getByEmail(String email);
}
