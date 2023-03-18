package edu.bbte.idde.nkim2061.server.service.impl;

import edu.bbte.idde.nkim2061.server.model.RealEstateAd;
import edu.bbte.idde.nkim2061.server.repository.DAOFactory;
import edu.bbte.idde.nkim2061.server.repository.RealEstateAdDAO;
import edu.bbte.idde.nkim2061.server.repository.RepositoryException;
import edu.bbte.idde.nkim2061.server.service.RealEstateAdService;
import edu.bbte.idde.nkim2061.server.service.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class ImplRealEstateAdService implements RealEstateAdService {

    private final RealEstateAdDAO realEstateAdDAO;

    public ImplRealEstateAdService() {
        realEstateAdDAO = DAOFactory.getInstance().getRealEstateAdDAO();
    }

    @Override
    public RealEstateAd save(RealEstateAd realEstateAd) {
        try {
            return realEstateAdDAO.create(realEstateAd);
        } catch (RepositoryException e) {
            log.error("Error while saving realEstateAd", e);
            throw new ServiceException("Error while saving realEstateAd", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            realEstateAdDAO.deleteById(id);
        } catch (RepositoryException e) {
            log.error("Error while deleting realEstateAd", e);
            throw new ServiceException("Error while deleting realEstateAd", e);
        }
    }

    @Override
    public RealEstateAd findById(Long id) {
        try {
            return realEstateAdDAO.getById(id);
        } catch (RepositoryException e) {
            log.error("Error while finding realEstateAd", e);
            throw new ServiceException("Error while finding realEstateAd", e);
        }
    }

    @Override
    public Collection<RealEstateAd> findAll() {
        try {
            return realEstateAdDAO.getAll();
        } catch (RepositoryException e) {
            log.error("Error while finding all realEstateAds", e);
            throw new ServiceException("Error while finding all realEstateAds", e);
        }
    }

    @Override
    public void update(RealEstateAd realEstate) {
        try {
            if (realEstateAdDAO.getById(realEstate.getId()) == null) {
                log.warn("Cannot update real estate ad with id {}, it does not exist", realEstate.getId());
            } else {
                realEstateAdDAO.update(realEstate);
            }
        } catch (RepositoryException e) {
            log.error("Error while updating realEstateAd", e);
            throw new ServiceException("Error while updating realEstateAd", e);
        }
    }

    @Override
    public RealEstateAd findByAddress(String address) {
        try {
            RealEstateAd advertisement = realEstateAdDAO.getByAddress(address);
            if (advertisement == null) {
                log.warn("RealEstateAd with address {} is not found", address);
            }
            return advertisement;
        } catch (RepositoryException e) {
            log.error("Error while getting realEstateAd by address", e);
            throw new ServiceException("Error while getting realEstateAd by address", e);
        }
    }
}
