package edu.bbte.idde.nkim2061.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.nkim2061.server.controller.exception.BadRequestException;
import edu.bbte.idde.nkim2061.server.controller.exception.ControllerException;
import edu.bbte.idde.nkim2061.server.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAgentCreationDTO;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAgentUpdateDTO;
import edu.bbte.idde.nkim2061.server.dto.outgoing.RealEstateAgentResponseDTO;
import edu.bbte.idde.nkim2061.server.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.server.service.RealEstateAgentService;
import edu.bbte.idde.nkim2061.server.service.ServiceException;
import edu.bbte.idde.nkim2061.server.service.ServiceFactory;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

@Slf4j
public class RealEstateAgentController {
    private final RealEstateAgentService realEstateAgentService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public RealEstateAgentController() {
        realEstateAgentService = ServiceFactory.getInstance().getRealEstateAgentService();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        objectMapper = new ObjectMapper();
    }

    /**
     * @throws ControllerException if the real estate agent could not be created
     */
    public RealEstateAgentResponseDTO save(RealEstateAgentCreationDTO creationDTO) throws BadRequestException {
        Set<ConstraintViolation<RealEstateAgentCreationDTO>> violations = validator.validate(creationDTO);
        if (violations.isEmpty()) {
            try {
                RealEstateAgent resp = realEstateAgentService.save(
                        objectMapper.convertValue(creationDTO, RealEstateAgent.class));
                return objectMapper.convertValue(resp, RealEstateAgentResponseDTO.class);
            } catch (ServiceException e) {
                log.error("Error while saving real estate agent", e);
                throw new ControllerException("Error at saving Real Estate Ad", e);
            }
        }
        throw new BadRequestException("Provided data is not sufficient or not appropriate");
    }

    /**
     * @throws ControllerException if there is an error while updating the real estate agent
     */
    public void deleteById(Long id) throws NotFoundException {
        try {
            if (realEstateAgentService.findById(id) == null) {
                throw new NotFoundException("RealEstateAd with id " + id + " does not exist");
            }
            realEstateAgentService.deleteById(id);
        } catch (ServiceException e) {
            log.error("Error while deleting real estate agent with id " + id, e);
            throw new ControllerException("Error while deleting real estate agent with id " + id, e);
        }
    }

    /**
     * @throws ControllerException if there is an error while updating the real estate agent
     */
    public RealEstateAgentResponseDTO findById(Long id) throws NotFoundException {
        try {
            RealEstateAgent realEstateAgent = realEstateAgentService.findById(id);
            if (realEstateAgent == null) {
                throw new NotFoundException("RealEstateAd with id " + id + " does not exist");
            }
            return objectMapper.convertValue(realEstateAgent, RealEstateAgentResponseDTO.class);
        } catch (ServiceException e) {
            log.error("Error while finding real estate agent with id " + id, e);
            throw new ControllerException("Error while finding real estate agent with id " + id, e);
        }
    }

    /**
     * @throws ControllerException if there is an error while updating the real estate agent
     * @throws BadRequestException if provided data is not sufficient or not appropriate
     */
    public void update(RealEstateAgentUpdateDTO updateDTO) throws NotFoundException, BadRequestException {
        Set<ConstraintViolation<RealEstateAgentUpdateDTO>> violations = validator.validate(updateDTO);
        if (violations.isEmpty()) {
            try {
                if (realEstateAgentService.findById(updateDTO.getId()) == null) {
                    throw new NotFoundException("RealEstateAd with id " + updateDTO.getId() + " does not exist");
                }
                realEstateAgentService.update(objectMapper.convertValue(updateDTO, RealEstateAgent.class));
                return;
            } catch (ServiceException e) {
                log.error("Error while updating real estate agent with id " + updateDTO.getId(), e);
                throw new ControllerException("Error while updating real estate agent with id " + updateDTO.getId(), e);
            }
        }
        throw new BadRequestException("Provided data is not sufficient or not appropriate");
    }

    /**
     * @throws ControllerException if there is an error while getting all real estate agents
     */
    public Collection<RealEstateAgentResponseDTO> findAll() {
        try {
            return realEstateAgentService.findAll().stream()
                    .map(advert -> objectMapper.convertValue(advert, RealEstateAgentResponseDTO.class)).toList();
        } catch (ServiceException e) {
            log.error("Error while finding all real estate agents", e);
            throw new ControllerException("Error while finding all real estate agents", e);
        }
    }
}
