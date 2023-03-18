package edu.bbte.idde.nkim2061.spring.controller.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAgentCreationDTO;
import edu.bbte.idde.nkim2061.spring.dto.incoming.RealEstateAgentUpdateDTO;
import edu.bbte.idde.nkim2061.spring.dto.outgoing.RealEstateAgentResponseDTO;
import edu.bbte.idde.nkim2061.spring.model.RealEstateAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class RealEstateAgentMapper {
    @Autowired
    private ObjectMapper objectMapper;

    public RealEstateAgentResponseDTO toResponseDTO(RealEstateAgent realEstateAgent) {
        return objectMapper.convertValue(realEstateAgent, RealEstateAgentResponseDTO.class);
    }

    public Collection<RealEstateAgentResponseDTO> toResponseDTO(Collection<RealEstateAgent> agents) {
        return agents.stream().map(this::toResponseDTO).toList();
    }

    public RealEstateAgent toEntity(RealEstateAgentUpdateDTO updateDTO) {
        return objectMapper.convertValue(updateDTO, RealEstateAgent.class);
    }

    public RealEstateAgent toEntity(RealEstateAgentCreationDTO creationDTO) {
        return objectMapper.convertValue(creationDTO, RealEstateAgent.class);
    }
}
