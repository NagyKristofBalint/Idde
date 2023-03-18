package edu.bbte.idde.nkim2061.server.service;

import edu.bbte.idde.nkim2061.server.model.RealEstateAd;

public interface RealEstateAdService extends Service<RealEstateAd> {
    RealEstateAd findByAddress(String address);
}
