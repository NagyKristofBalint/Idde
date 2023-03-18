package edu.bbte.idde.nkim2061.spring.service.impl;

import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.repository.RealEstateAdDAO;
import edu.bbte.idde.nkim2061.spring.repository.RepositoryException;
import edu.bbte.idde.nkim2061.spring.service.RealEstateAdService;
import edu.bbte.idde.nkim2061.spring.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class ImplRealEstateAdService implements RealEstateAdService {

    @Autowired
    private RealEstateAdDAO realEstateAdDAO;

    @Override
    public RealEstateAd save(RealEstateAd realEstateAd) {
        try {
            return realEstateAdDAO.save(realEstateAd);
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
            return realEstateAdDAO.findById(id).orElse(null);
        } catch (RepositoryException e) {
            log.error("Error while finding realEstateAd", e);
            throw new ServiceException("Error while finding realEstateAd", e);
        }
    }

    @Override
    public Collection<RealEstateAd> findAll() {
        try {
            return realEstateAdDAO.findAll();
        } catch (RepositoryException e) {
            log.error("Error while finding all realEstateAds", e);
            throw new ServiceException("Error while finding all realEstateAds", e);
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
