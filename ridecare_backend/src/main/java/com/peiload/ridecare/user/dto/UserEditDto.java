package com.peiload.ridecare.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEditDto {

    @Email(message = "Enter a valid email address.")
    private String email;

    private String companyName;
}
