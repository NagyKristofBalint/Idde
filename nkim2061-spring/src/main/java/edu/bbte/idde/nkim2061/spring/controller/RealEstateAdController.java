package edu.bbte.idde.nkim2061.spring.controller;

import edu.bbte.idde.nkim2061.spring.controller.exception.ControllerException;
import edu.bbte.idde.nkim2061.spring.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.spring.controller.mapping.RealEstateAdMapper;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAdCreationDTO;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAdUpdateDTO;
import edu.bbte.idde.nkim2061.spring.dto.outgoing.RealEstateAdResponseDTO;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import edu.bbte.idde.nkim2061.spring.service.RealEstateAdService;
import edu.bbte.idde.nkim2061.spring.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/real-estate-ads")
public class RealEstateAdController {

    @Autowired
    private RealEstateAdService realEstateAdService;

    @Autowired
    private RealEstateAdMapper realEstateAdMapper;

    @GetMapping
    public Collection<RealEstateAdResponseDTO> getAll() {
        log.info("GET /real-estate-ads");
        try {
            Collection<RealEstateAd> list = realEstateAdService.findAll();
            log.info("All RealEstateAds are queried successfully");
            return realEstateAdMapper.toResponseDTO(list);
        } catch (ServiceException e) {
            log.error("Error at getting any RealEstateAd: {}", e.getMessage());
            throw new ControllerException("Error at getting any RealEstateAd", e);
        }
    }

    @GetMapping("/{id}")
    public RealEstateAdResponseDTO getById(@PathVariable Long id) {
        log.info("GET /real-estate-ads/{}", id);
        try {
            RealEstateAd advertisement = realEstateAdService.findById(id);
            if (advertisement == null) {
                throw new NotFoundException("Real estate ad with id " + id + " is not found");
            }
            return realEstateAdMapper.toResponseDTO(advertisement);
        } catch (ServiceException e) {
            log.error("Error at getting RealEstateAd by id: {}", id, e);
            throw new ControllerException("Error at getting RealEstateAd by id: " + id, e);
        }
    }

    @GetMapping(params = {"address"})
    public RealEstateAdResponseDTO findByAddress(@RequestParam String address) {
        log.info("GET /real-estate-ads?address={}", address);
        try {
            RealEstateAd advertisement = realEstateAdService.findByAddress(address);
            if (advertisement == null) {
                throw new NotFoundException("Real estate ad with address " + address + " is not found");
            }
            return realEstateAdMapper.toResponseDTO(advertisement);
        } catch (ServiceException e) {
            log.error("Error at getting RealEstateAd by address: {}", address, e);
            throw new ControllerException("Error at getting RealEstateAd by address: " + address, e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealEstateAdResponseDTO create(@RequestBody @Valid RealEstateAdCreationDTO creationDTO) {
        log.info("POST /real-estate-ads");
        try {
            RealEstateAd advertisement = realEstateAdMapper.toEntity(creationDTO);
            RealEstateAd savedAd = realEstateAdService.save(advertisement);
            RealEstateAdResponseDTO response = realEstateAdMapper.toResponseDTO(savedAd);
            log.info("RealEstateAd with id {} has been created successfully", savedAd.getId());
            return response;
        } catch (ServiceException e) {
            log.error("Error at creating RealEstateAd {}", e.getMessage());
            throw new ControllerException("Error at creating RealEstateAd", e);
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid RealEstateAdUpdateDTO updateDTO) {
        log.info("PUT /real-estate-ads");
        try {
            if (realEstateAdService.findById(id) == null) {
                log.info("Cannot update RealEstateAd with id {}, it does not exist", id);
                throw new NotFoundException("Cannot update entity with id "
                        + id + ", it does not exist");
            }
            RealEstateAd advertisement = realEstateAdMapper.toEntity(updateDTO);
            advertisement.setId(id);
            realEstateAdService.save(advertisement);
            log.info("RealEstateAd with id {} has been updated successfully", advertisement.getId());
        } catch (ServiceException e) {
            log.error("Error at updating RealEstateAd with id {}", id);
            throw new ControllerException("Error at updating RealEstateAd with id " + id, e);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("DELETE /real-estate-ads?id={}", id);
        try {
            if (realEstateAdService.findById(id) == null) {
                log.info("Cannot delete RealEstateAd with id {}, it does not exist", id);
                throw new NotFoundException("Cannot delete entity with id " + id + ", it does not exist");
            }
            realEstateAdService.deleteById(id);
            log.info("RealEstateAd with id {} has been deleted successfully", id);
        } catch (ServiceException e) {
            log.error("Error at deleting RealEstateAd by id: {}", id, e);
            throw new ControllerException("Error at deleting RealEstateAd by id: " + id, e);
        }
    }
}
