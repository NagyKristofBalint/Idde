package edu.bbte.idde.nkim2061.spring.controller;

import edu.bbte.idde.nkim2061.spring.controller.exception.ControllerException;
import edu.bbte.idde.nkim2061.spring.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.spring.controller.mapping.RealEstateAdMapper;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAdCreationDTO;
import edu.bbte.idde.nkim2061.spring.dto.outgoing.RealEstateAdResponseDTO;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.spring.service.RealEstateAgentService;
import edu.bbte.idde.nkim2061.spring.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/real-estate-agents/{id}/ads")
public class RealEstateAgentWithAdController {

    @Autowired
    private RealEstateAgentService realEstateAgentService;

    @Autowired
    private RealEstateAdMapper realEstateAdMapper;

    @GetMapping
    public Collection<RealEstateAdResponseDTO> getAdsOf(@PathVariable Long id) {
        log.info("GET /real-estate-agents-with-ads/{}", id);
        try {
            RealEstateAgent realEstateAgent = realEstateAgentService.findById(id);
            if (realEstateAgent == null) {
                throw new NotFoundException("Agent with id " + id + " does not exist");
            }
            return realEstateAdMapper.toResponseDTO(realEstateAgentService.getAdvertisementsOfAgent(id));
        } catch (ServiceException | PersistenceException e) {
            log.error("Error at getting RealEstateAds of agent with id: {}", id, e);
            throw new ControllerException("Error at getting RealEstateAds of agent with id: " + id, e);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addAdvertisementToAgent(@PathVariable Long id,
                                        @RequestBody @Valid RealEstateAdCreationDTO adCreationDTO) {
        log.info("PUT /real-estate-agents-with-ads/{}/ads", id);
        try {
            RealEstateAgent realEstateAgent = realEstateAgentService.findById(id);
            if (realEstateAgent == null) {
                throw new NotFoundException("Agent with id " + id + " does not exist");
            }
            RealEstateAd advertisement = realEstateAdMapper.toEntity(adCreationDTO);
            realEstateAgentService.addAdvertisementToAgent(id, advertisement);
        } catch (ServiceException | PersistenceException e) {
            log.error("Error at adding RealEstateAd to agent with id: {}", id, e);
            throw new ControllerException("Error at adding RealEstateAd to agent with id: " + id, e);
        }
    }

    @DeleteMapping
    public void deleteAdvertisementFromAgent(@PathVariable Long id,
                                             @RequestParam Long advertisementId) {
        log.info("DELETE /real-estate-agents-with-ads");
        try {
            RealEstateAgent agent = realEstateAgentService.findById(id);
            if (agent == null) {
                throw new NotFoundException("Real estate agent with id " + id + " is not found");
            }
            if (agent.getRealEstateAds().stream()
                    .noneMatch(realEstateAd -> realEstateAd.getId().equals(advertisementId))) {
                throw new NotFoundException("Real estate agent with id " + id
                        + " does not have advertisement with id " + advertisementId);
            }
            realEstateAgentService.deleteAdvertisement(id, advertisementId);
        } catch (ServiceException | PersistenceException e) {
            log.error("Error at deleting RealEstateAd from agent with id: {}", id);
            throw new ControllerException("Error at deleting RealEstateAd from agent with id: " + id);
        }
    }
}
