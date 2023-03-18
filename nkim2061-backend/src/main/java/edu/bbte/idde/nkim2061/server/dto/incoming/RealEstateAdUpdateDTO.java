package edu.bbte.idde.nkim2061.server.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateAdUpdateDTO {
    @NotNull
    private Long id;
    private Date date;
    private String description;
    private String address;
    private String city;
    private Float price;
}
