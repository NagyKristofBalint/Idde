package edu.bbte.idde.nkim2061.server.service;

import edu.bbte.idde.nkim2061.server.service.impl.ImplServiceFactory;

public abstract class ServiceFactory {

    private static ServiceFactory instance;

    protected ServiceFactory() {
    }

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ImplServiceFactory();
        }
        return instance;
    }

    public abstract RealEstateAdService getRealEstateAdService();

    public abstract RealEstateAgentService getRealEstateAgentService();
}
