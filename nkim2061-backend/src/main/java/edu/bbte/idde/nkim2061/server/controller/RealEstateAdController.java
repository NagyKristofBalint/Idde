package edu.bbte.idde.nkim2061.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.nkim2061.server.controller.exception.BadRequestException;
import edu.bbte.idde.nkim2061.server.controller.exception.ControllerException;
import edu.bbte.idde.nkim2061.server.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAdCreationDTO;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAdUpdateDTO;
import edu.bbte.idde.nkim2061.server.dto.outgoing.RealEstateAdResponseDTO;
import edu.bbte.idde.nkim2061.server.model.RealEstateAd;
import edu.bbte.idde.nkim2061.server.service.RealEstateAdService;
import edu.bbte.idde.nkim2061.server.service.ServiceException;
import edu.bbte.idde.nkim2061.server.service.ServiceFactory;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

@Slf4j
public class RealEstateAdController {
    private final RealEstateAdService realEstateAdService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public RealEstateAdController() {
        realEstateAdService = ServiceFactory.getInstance().getRealEstateAdService();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        objectMapper = new ObjectMapper();
    }

    /**
     * @throws ControllerException if the real estate ad could not be created
     */
    public RealEstateAdResponseDTO save(RealEstateAdCreationDTO creationDTO) throws BadRequestException {
        Set<ConstraintViolation<RealEstateAdCreationDTO>> violations = validator.validate(creationDTO);
        if (violations.isEmpty()) {
            try {
                RealEstateAd resp = realEstateAdService.save(
                        objectMapper.convertValue(creationDTO, RealEstateAd.class));
                return objectMapper.convertValue(resp, RealEstateAdResponseDTO.class);
            } catch (ServiceException e) {
                log.error("Error while saving real estate ad", e);
                throw new ControllerException("Error at saving Real Estate Ad", e);
            }
        }
        throw new BadRequestException("Provided data is not sufficient or not appropriate");
    }

    /**
     * @throws ControllerException if there is an error while updating the real estate ad
     */
    public void deleteById(Long id) throws NotFoundException {
        try {
            if (realEstateAdService.findById(id) == null) {
                throw new NotFoundException("RealEstateAd with id " + id + " does not exist");
            }
            realEstateAdService.deleteById(id);
        } catch (ServiceException e) {
            log.error("Error while deleting real estate ad with id " + id, e);
            throw new ControllerException("Error while deleting real estate ad with id " + id, e);
        }
    }

    /**
     * @throws ControllerException if there is an error while updating the real estate ad
     */
    public RealEstateAdResponseDTO findById(Long id) throws NotFoundException {
        try {
            RealEstateAd realEstateAd = realEstateAdService.findById(id);
            if (realEstateAd == null) {
                throw new NotFoundException("RealEstateAd with id " + id + " does not exist");
            }
            return objectMapper.convertValue(realEstateAd, RealEstateAdResponseDTO.class);
        } catch (ServiceException e) {
            log.error("Error while finding real estate ad with id " + id, e);
            throw new ControllerException("Error while finding real estate ad with id " + id, e);
        }
    }

    /**
     * @throws ControllerException if there is an error while updating the real estate ad
     */
    public void update(RealEstateAdUpdateDTO updateDTO) throws NotFoundException, BadRequestException {
        Set<ConstraintViolation<RealEstateAdUpdateDTO>> violations = validator.validate(updateDTO);
        if (violations.isEmpty()) {
            try {
                if (realEstateAdService.findById(updateDTO.getId()) == null) {
                    throw new NotFoundException("RealEstateAd with id " + updateDTO.getId() + " does not exist");
                }
                realEstateAdService.update(objectMapper.convertValue(updateDTO, RealEstateAd.class));
                return;
            } catch (ServiceException e) {
                log.error("Error while updating real estate ad with id " + updateDTO.getId(), e);
                throw new ControllerException("Error while updating real estate ad with id " + updateDTO.getId(), e);
            }
        }
        throw new BadRequestException("Provided data is not sufficient or not appropriate");
    }

    /**
     * @throws ControllerException if there is an error while getting all real estate ads
     */
    public Collection<RealEstateAdResponseDTO> findAll() {
        try {
            return realEstateAdService.findAll().stream()
                    .map(advert -> objectMapper.convertValue(advert, RealEstateAdResponseDTO.class)).toList();
        } catch (ServiceException e) {
            log.error("Error while finding all real estate ads", e);
            throw new ControllerException("Error while finding all real estate ads", e);
        }
    }
}
