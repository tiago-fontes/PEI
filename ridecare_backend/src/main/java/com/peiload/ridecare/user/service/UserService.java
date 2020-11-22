package com.peiload.ridecare.user.service;

import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.dto.UserSetDto;
import com.peiload.ridecare.user.dto.UserShowDto;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jtu;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenUtil jtu){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jtu = jtu;
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public UserShowDto getUser(String authorizationToken) {
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        return new UserShowDto(findByEmail(email));
    }

    public List<UserShowDto> getAllUsers() {
        List<UserShowDto> allUsers = new ArrayList<>();
        this.userRepository.findAll().forEach(user -> allUsers.add(new UserShowDto(user)));
        return allUsers;
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

    public void deleteUser(String authorizationToken) {
        String email = jtu.getEmailFromAuthorizationString(authorizationToken);
        this.userRepository.delete(findByEmail(email));
    }


}
