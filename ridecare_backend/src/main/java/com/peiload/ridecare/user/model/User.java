package com.peiload.ridecare.user.model;

import com.peiload.ridecare.car.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @Email
    private String email;
    private String companyName;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Car> cars;
}


