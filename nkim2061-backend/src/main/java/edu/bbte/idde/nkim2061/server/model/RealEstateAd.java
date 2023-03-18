package edu.bbte.idde.nkim2061.server.model;

import lombok.*;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateAd extends BaseEntity {
    private Date date;
    private String description;
    private String address;
    private String city;
    private Float price;

    @Override
    public String toString() {
        return "RealEstateAd{"
                + "id=" + id
                + ", date=" + date
                + ", description='" + description + '\''
                + ", address='" + address + '\''
                + ", city='" + city + '\''
                + ", price=" + price
                + '}';
    }
}