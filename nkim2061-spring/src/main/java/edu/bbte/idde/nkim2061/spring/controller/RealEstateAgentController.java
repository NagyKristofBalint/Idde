package edu.bbte.idde.nkim2061.spring.controller;

import edu.bbte.idde.nkim2061.spring.controller.exception.ControllerException;
import edu.bbte.idde.nkim2061.spring.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.spring.controller.mapping.RealEstateAgentMapper;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAgentCreationDTO;
import edu.bbte.idde.nkim2061.spring.dto.outgoing.RealEstateAgentResponseDTO;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import edu.bbte.idde.nkim2061.spring.service.RealEstateAgentService;
import edu.bbte.idde.nkim2061.spring.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/real-estate-agents")
public class RealEstateAgentController {

    @Autowired
    private RealEstateAgentService realEstateAgentService;

    @Autowired
    private RealEstateAgentMapper realEstateAgentMapper;

    @GetMapping
    public Collection<RealEstateAgentResponseDTO> getAll() {
        log.info("GET /real-estate-agents");
        try {
            Collection<RealEstateAgent> list = realEstateAgentService.findAll();
            log.info("All RealEstateAds are queried successfully");
            return realEstateAgentMapper.toResponseDTO(list);
        } catch (ServiceException e) {
            log.error("Error at getting any RealEstateAgent: {}", e.getMessage());
            throw new ControllerException("Error at getting any RealEstateAgent", e);
        }
    }

    @GetMapping("/{id}")
    public RealEstateAgentResponseDTO getById(@PathVariable Long id) {
        log.info("GET /real-estate-agents/{}", id);
        try {
            RealEstateAgent agent = realEstateAgentService.findById(id);
            if (agent == null) {
                throw new NotFoundException("Real estate agent with id " + id + " is not found");
            }
            return realEstateAgentMapper.toResponseDTO(agent);
        } catch (ServiceException e) {
            log.error("Error at getting RealEstateAgent by id: {}", id, e);
            throw new ControllerException("Error at getting RealEstateAgent by id: " + id, e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealEstateAgentResponseDTO create(@RequestBody @Valid RealEstateAgentCreationDTO creationDTO) {
        log.info("POST /real-estate-agents");
        try {
            RealEstateAgent agent = realEstateAgentMapper.toEntity(creationDTO);
            RealEstateAgent savedAgent = realEstateAgentService.save(agent);
            RealEstateAgentResponseDTO response = realEstateAgentMapper.toResponseDTO(savedAgent);
            log.info("RealEstateAgent with id {} has been created successfully", savedAgent.getId());
            return response;
        } catch (ServiceException e) {
            throw new ControllerException("Error at creating RealEstateAgent", e);
        }
    }
}
