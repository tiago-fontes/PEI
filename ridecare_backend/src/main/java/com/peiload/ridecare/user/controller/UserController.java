package com.peiload.ridecare.user.controller;

import com.peiload.ridecare.user.dto.UserSetDto;
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

    //TODO: remover opção de editar password desta função e criar um endpoint novo para isso em que é necessário meter a password antiga
    //TODO: verificar mensagem de erros quando é enviado um campo vazio ou quando o campo de email tem um email invalido
    @PatchMapping
    public void editUser(@RequestHeader("Authorization") String authorizationToken, @RequestBody UserSetDto userSetDto){
        this.userService.editUser(authorizationToken, userSetDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader("Authorization") String authorizationToken){
        this.userService.deleteUser(authorizationToken);
    }
}
