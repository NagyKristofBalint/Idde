package edu.bbte.idde.nkim2061.spring.repository;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;

public interface RealEstateAdDAO extends DAO<RealEstateAd> {
    RealEstateAd getByAddress(String address);
}
