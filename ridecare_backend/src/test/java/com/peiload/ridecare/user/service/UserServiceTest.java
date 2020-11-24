package com.peiload.ridecare.user.service;

import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.dto.UserShowDto;
import com.peiload.ridecare.user.model.TestUser;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.peiload.ridecare.user.model.TestUser.getUsersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private JwtTokenUtil jwtTokenUtilMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService testObj;

    @BeforeEach
    public void setup(){
        testObj = new UserService(passwordEncoderMock, userRepositoryMock, jwtTokenUtilMock);
    }

    @Test
    void findByEmail_happyPath() {
        User user1 = TestUser.getUser1();
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.of(user1));

        User result = testObj.findByEmail(user1.getEmail());

        assertNotNull(result);
        assertEquals(user1.getId(), result.getId());
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getCompanyName(), result.getCompanyName());
    }

    @Test
    void findByEmail_whenEmailDoesntExist_shouldRaiseAnException() {
        when(userRepositoryMock.findByEmail(any())).thenReturn(Optional.ofNullable(null));

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.findByEmail(any()));

        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    }

    @Test
    void getUser_happyPath() {
        User user1 = TestUser.getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.of(user1));

        UserShowDto result = testObj.getUser("Bearer token");

        assertNotNull(result);
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getCompanyName(), result.getCompanyName());
    }

    @Test
    void getAllUsers() {
        List<User> userList = getUsersList();
        when(userRepositoryMock.findAll()).thenReturn(userList);

        List<UserShowDto> result = testObj.getAllUsers();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    void createUser() {
        //TODO
    }

    @Test
    void deleteUser() {
        //TODO
    }
}