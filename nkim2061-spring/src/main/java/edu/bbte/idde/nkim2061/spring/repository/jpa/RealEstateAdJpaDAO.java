package edu.bbte.idde.nkim2061.spring.repository.jpa;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAdDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface RealEstateAdJpaDAO extends JpaRepository<RealEstateAd, Long>, RealEstateAdDAO {
}
