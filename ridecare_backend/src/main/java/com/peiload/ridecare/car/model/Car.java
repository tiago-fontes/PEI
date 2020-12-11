package com.peiload.ridecare.car.model;

import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.car.dto.CarCreateDto;
import com.peiload.ridecare.statusHistory.model.StatusHistory;
import com.peiload.ridecare.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Integer sensorId;

    @NotNull
    @NotEmpty
    private String status;

    @NotNull
    @NotEmpty
    private String licensePlate;

    @Lob
    private String image;

    private String brand;
    private String model;
    private int year;
    private int numberOfDoors;
    private int numberOfSeats;
    private String transmission;
    private String fuel;

    private Float latitude;
    private Float longitude;

    @NotNull
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "car")
    private List<Anomaly> anomalies;

    @OneToMany(mappedBy = "car")
    private List<StatusHistory> statusHistory;

    public Car(CarCreateDto car, User user) {
        this.sensorId = car.getSensorId();
        this.status = "offline";
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

        this.latitude = (float) 0;
        this.latitude = (float) 0;

        this.anomalies = new ArrayList<>();
        this.statusHistory = new ArrayList<>();
    }
}
