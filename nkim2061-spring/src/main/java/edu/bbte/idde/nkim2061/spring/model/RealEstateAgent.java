package edu.bbte.idde.nkim2061.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "real_estate_agents")
public class RealEstateAgent extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer age;

    @OneToMany(mappedBy = "realEstateAgent", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private List<RealEstateAd> realEstateAds = new ArrayList<>();
}
