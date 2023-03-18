package edu.bbte.idde.nkim2061.spring.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateAdResponseDTO {
    private Long id;
    private Date date;
    private String description;
    private String address;
    private String city;
    private Float price;
}
