package edu.bbte.idde.nkim2061.spring.controller.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAdCreationDTO;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAdUpdateDTO;
import edu.bbte.idde.nkim2061.spring.dto.outgoing.RealEstateAdResponseDTO;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class RealEstateAdMapper {
    @Autowired
    private ObjectMapper objectMapper;

    public RealEstateAdResponseDTO toResponseDTO(RealEstateAd realEstateAd) {
        return objectMapper.convertValue(realEstateAd, RealEstateAdResponseDTO.class);
    }

    public Collection<RealEstateAdResponseDTO> toResponseDTO(Collection<RealEstateAd> ads) {
        return ads.stream().map(this::toResponseDTO).toList();
    }

    public RealEstateAd toEntity(RealEstateAdUpdateDTO updateDTO) {
        return objectMapper.convertValue(updateDTO, RealEstateAd.class);
    }

    public RealEstateAd toEntity(RealEstateAdCreationDTO creationDTO) {
        return objectMapper.convertValue(creationDTO, RealEstateAd.class);
    }
}
