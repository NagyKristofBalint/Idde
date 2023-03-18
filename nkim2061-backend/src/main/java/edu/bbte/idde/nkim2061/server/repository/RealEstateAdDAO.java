package edu.bbte.idde.nkim2061.server.repository;

import edu.bbte.idde.nkim2061.server.model.RealEstateAd;

public interface RealEstateAdDAO extends DAO<RealEstateAd> {
    RealEstateAd getByAddress(String address);
}
