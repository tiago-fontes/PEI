package com.peiload.ridecare.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSetDto {
    @NotNull
    @Email(message = "Enter a valid email address.")
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String companyName;
    @NotNull
    @NotEmpty
    private String password;
}
