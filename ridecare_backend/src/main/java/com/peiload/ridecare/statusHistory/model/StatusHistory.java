package com.peiload.ridecare.statusHistory.model;

import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.statusHistory.dto.StatusHistoryCreateDto;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String status;

    @NotNull
    private Date date;

    @NotNull
    @ManyToOne
    private Car car;

    public StatusHistory(StatusHistoryCreateDto statusHistorySetDto){
        this.status = statusHistorySetDto.getStatus();
        this.date = statusHistorySetDto.getDate();
    }
}
