package com.peiload.ridecare.user.model;

import com.peiload.ridecare.car.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @Email(message = "Enter a valid email address.")
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String companyName;
    @NotNull
    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Car> cars;

}


