package com.peiload.ridecare.car.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="statusHistory")
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private CarStatus status;

    @NotNull
    private Date date;

    @NotNull
    @ManyToOne
    private Car car;

    public StatusHistory(@NotNull CarStatus status, @NotNull Date date, @NotNull Car car) {
        this.status = status;
        this.date = date;
        this.car = car;
    }
}
