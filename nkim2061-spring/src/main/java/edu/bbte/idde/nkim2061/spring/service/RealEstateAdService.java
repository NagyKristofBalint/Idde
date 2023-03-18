package edu.bbte.idde.nkim2061.spring.service;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;

public interface RealEstateAdService extends Service<RealEstateAd> {
    RealEstateAd findByAddress(String address);
}
