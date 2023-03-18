package edu.bbte.idde.nkim2061.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateAgent extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer age;

    @Override
    public String toString() {
        return "RealEstateAgent{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", age='" + age + '\''
                + '}';
    }
}
