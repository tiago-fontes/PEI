package com.peiload.ridecare.user.controller;

import com.peiload.ridecare.user.dto.PasswordEditDto;
import com.peiload.ridecare.user.dto.UserEditDto;
import com.peiload.ridecare.user.dto.UserShowDto;
import com.peiload.ridecare.user.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "UserController")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public UserShowDto getUser(@RequestHeader("Authorization") String authorizationToken){
        return this.userService.getUser(authorizationToken);
    }

    @PatchMapping
    public void editUser(@RequestHeader("Authorization") String authorizationToken, @RequestBody @Valid UserEditDto userEditDto){
        this.userService.editUser(authorizationToken, userEditDto);
    }

    @PatchMapping(path="/change/password")
    public void editPassword(@RequestHeader("Authorization") String authorizationToken, @RequestBody PasswordEditDto passwordEditDto){
        this.userService.editPassword(authorizationToken, passwordEditDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader("Authorization") String authorizationToken){
        this.userService.deleteUser(authorizationToken);
    }
}
