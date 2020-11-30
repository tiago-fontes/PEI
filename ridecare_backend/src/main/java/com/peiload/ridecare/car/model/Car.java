package com.peiload.ridecare.car.model;

import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.car.dto.CarSetDto;
import com.peiload.ridecare.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String licensePlate;
    @ManyToOne
    private User user;

    @Lob
    private String image;

    private String brand;
    private String model;
    private int year;
    private int numberOfDoors;
    private int numberOfSeats;
    private String transmission;
    private String fuel;

    private String raspberryInfo;

    @OneToMany(mappedBy = "car")
    private List<Anomaly> anomalies;


    public Car(CarSetDto car, User user) {
        this.licensePlate = car.getLicensePlate();
        this.user = user;
        this.image = car.getImage();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.numberOfDoors = car.getNumberOfDoors();
        this.numberOfSeats = car.getNumberOfSeats();
        this.transmission = car.getTransmission();
        this.fuel = car.getFuel();
        this.raspberryInfo = "";

        this.anomalies = new ArrayList<>();
    }
}
