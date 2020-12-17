package com.peiload.ridecare.user.service;

import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.dto.PasswordEditDto;
import com.peiload.ridecare.user.dto.UserEditDto;
import com.peiload.ridecare.user.dto.UserSetDto;
import com.peiload.ridecare.user.dto.UserShowDto;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenUtil jwtTokenUtil){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist."));
    }

    public UserShowDto getUser(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        return new UserShowDto(findByEmail(email));
    }

    public List<UserShowDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(UserShowDto::new).collect(Collectors.toList());
    }

    public UserShowDto createUser(UserSetDto userSetDto) {
        Optional<User> userOptional = userRepository.findByEmail(userSetDto.getEmail());
        if (userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else{
            User createdUser = User.builder()
                    .companyName(userSetDto.getCompanyName())
                    .email(userSetDto.getEmail())
                    .password(passwordEncoder.encode(userSetDto.getPassword()))
                    .build();

            userRepository.save(createdUser);

            return new UserShowDto(createdUser);
        }
    }

    public void editUser(String authorizationToken, UserEditDto userEditDto) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = findByEmail(email);

        if(userEditDto.getEmail() != null){
            user.setEmail(userEditDto.getEmail());
        }
        if(userEditDto.getCompanyName() != null){
            user.setCompanyName(userEditDto.getCompanyName());
        }

        this.userRepository.save(user);
    }

    public void editPassword(String authorizationToken, PasswordEditDto passwordEditDto){
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = findByEmail(email);

        if(passwordEncoder.matches(passwordEditDto.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(passwordEditDto.getNewPassword()));
            this.userRepository.save(user);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
    }

    public void deleteUser(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        this.userRepository.delete(findByEmail(email));
    }
}
