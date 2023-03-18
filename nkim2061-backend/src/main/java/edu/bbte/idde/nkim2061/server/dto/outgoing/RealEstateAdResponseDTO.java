package edu.bbte.idde.nkim2061.server.dto.outgoing;

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

    @Override
    public String toString() {
        return  "id=" + id
                + ", date=" + date
                + ", description='" + description + '\''
                + ", address='" + address + '\''
                + ", city='" + city + '\''
                + ", price=" + price;
    }
}
