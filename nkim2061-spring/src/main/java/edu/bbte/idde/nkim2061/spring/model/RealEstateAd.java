package edu.bbte.idde.nkim2061.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "real_estate_ads")
public class RealEstateAd extends BaseEntity {
    @Column(name = "ad_date")
    private Date date;
    private String description;
    private String address;
    private String city;
    private Float price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private RealEstateAgent realEstateAgent;
}