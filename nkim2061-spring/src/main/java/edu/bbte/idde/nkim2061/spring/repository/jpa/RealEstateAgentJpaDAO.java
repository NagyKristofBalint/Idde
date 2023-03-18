package edu.bbte.idde.nkim2061.spring.repository.jpa;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAgentDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface RealEstateAgentJpaDAO extends JpaRepository<RealEstateAgent, Long>, RealEstateAgentDAO {
}
