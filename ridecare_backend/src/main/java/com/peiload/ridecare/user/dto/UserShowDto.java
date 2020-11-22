package com.peiload.ridecare.user.dto;

import com.peiload.ridecare.car.dto.CarShowDto;
import com.peiload.ridecare.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserShowDto {
    private String email;
    private String companyName;
    private List<CarShowDto> cars;

    public UserShowDto(User user){
        this.email = user.getEmail();
        this.companyName = user.getCompanyName();
        if(user.getCars() == null){
            this.cars = new ArrayList<>();
        }
        else {
            this.cars = user.getCars().stream().map(CarShowDto::new).collect(Collectors.toList());
        }
    }

}
