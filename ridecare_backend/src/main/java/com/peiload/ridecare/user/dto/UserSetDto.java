package com.peiload.ridecare.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSetDto {
    @NotNull
    private String email;
    @NotNull
    private String companyName;
    @NotNull
    private String password;
}
