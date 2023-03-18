package edu.bbte.idde.nkim2061.spring.dto.incoming;

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
    private Date date;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private Float price;
}
