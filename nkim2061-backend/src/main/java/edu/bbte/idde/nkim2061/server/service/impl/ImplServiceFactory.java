package edu.bbte.idde.nkim2061.server.service.impl;

import edu.bbte.idde.nkim2061.server.service.RealEstateAdService;
import edu.bbte.idde.nkim2061.server.service.RealEstateAgentService;
import edu.bbte.idde.nkim2061.server.service.ServiceFactory;

public class ImplServiceFactory extends ServiceFactory {

    private static RealEstateAdService realEstateAdService;
    private static RealEstateAgentService realEstateAgentService;

    @Override
    public synchronized RealEstateAdService getRealEstateAdService() {
        if (realEstateAdService == null) {
            realEstateAdService = new ImplRealEstateAdService();
        }
        return realEstateAdService;
    }

    @Override
    public synchronized RealEstateAgentService getRealEstateAgentService() {
        if (realEstateAgentService == null) {
            realEstateAgentService = new ImplRealEstateAgentService();
        }
        return realEstateAgentService;
    }
}
