package com.peiload.ridecare.unitTest.user.service;

import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.unitTest.user.model.TestUser;
import com.peiload.ridecare.user.dto.UserSetDto;
import com.peiload.ridecare.user.model.User;
import com.peiload.ridecare.user.repository.UserRepository;
import com.peiload.ridecare.user.dto.UserShowDto;
import com.peiload.ridecare.user.service.UserService;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        User result = testObj.findByEmail(user1.getEmail());

        assertNotNull(result);
        assertEquals(user1.getId(), result.getId());
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getCompanyName(), result.getCompanyName());
    }

    @Test
    void findByEmail_whenEmailDoesntExist_shouldRaiseAnException() {
        when(userRepositoryMock.findByEmail(any())).thenReturn(Optional.ofNullable(null));

        String email = any();
        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.findByEmail(email));

        assertEquals(HttpStatus.BAD_REQUEST.toString() + " \"User does not exist.\"", exception.getMessage());
    }

    @Test
    void getUser_happyPath() {
        User user1 = TestUser.getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        UserShowDto result = testObj.getUser("Bearer token");

        assertNotNull(result);
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getCompanyName(), result.getCompanyName());
    }

    @Test
    void getAllUsers_happyPath() {
        List<User> userList = TestUser.getUsersList();
        when(userRepositoryMock.findAll()).thenReturn(userList);

        List<UserShowDto> result = testObj.getAllUsers();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    void createUser_happyPath() {
        UserSetDto user1 = TestUser.getUserSetDto1();
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.ofNullable(null));
        when(passwordEncoderMock.encode(user1.getPassword())).thenReturn(user1.getPassword());

        UserShowDto result = testObj.createUser(user1);

        //Assert result
        assertNotNull(result);
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getCompanyName(), result.getCompanyName());

        //Assert the saved object
        verify(userRepositoryMock, times(1)).save(
                argThat(newUser -> {
                    assertThat(newUser).isNotNull();
                    Assertions.assertThat(newUser.getEmail()).isEqualTo(user1.getEmail());
                    Assertions.assertThat(newUser.getCompanyName()).isEqualTo(user1.getCompanyName());
                    Assertions.assertThat(newUser.getPassword()).isEqualTo(user1.getPassword());
                    return true;
                })
        );
    }

    @Test
    void createUser_whenEmailAlreadyExists_shouldRaiseAnException() {
        User user1 = TestUser.getUser1();
        UserSetDto userSetDto1 = TestUser.getUserSetDto1();
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.createUser(userSetDto1));

        assertEquals(HttpStatus.FORBIDDEN.toString(), exception.getMessage());
    }

    @Test
    void deleteUser_happyPath() {
        User user1 = TestUser.getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        testObj.deleteUser("Bearer token");

        verify(userRepositoryMock, times(1)).delete(
                argThat(userToDelete -> {
                    assertThat(userToDelete).isNotNull();
                    Assertions.assertThat(userToDelete.getId()).isEqualTo(user1.getId());
                    Assertions.assertThat(userToDelete.getEmail()).isEqualTo(user1.getEmail());
                    Assertions.assertThat(userToDelete.getCompanyName()).isEqualTo(user1.getCompanyName());
                    Assertions.assertThat(userToDelete.getPassword()).isEqualTo(user1.getPassword());
                    return true;
                })
        );
    }

    @Test
    void deleteUser_whenUserDoesntExist_shouldRaiseAnException() {
        when(userRepositoryMock.findByEmail(any())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.deleteUser("Bearer token"));

        assertEquals(HttpStatus.BAD_REQUEST.toString() + " \"User does not exist.\"", exception.getMessage());
    }

    @Test
    void editUser_happyPath(){
        User user1 = TestUser.getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        UserSetDto userSetDto = TestUser.getUserSetDto2();

        testObj.editUser("Bearer token", userSetDto);

        verify(userRepositoryMock, times(1)).save(
                argThat(u -> {
                    assertThat(u).isNotNull();
                    Assertions.assertThat(u.getEmail()).isEqualTo(userSetDto.getEmail());
                    Assertions.assertThat(u.getCompanyName()).isEqualTo(userSetDto.getCompanyName());
                    return true;
                })
        );
    }

    @Test
    void editUser_whenUserDoesntExist_shouldRaiseAnException(){
        User user1 = TestUser.getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.ofNullable(null));

        UserSetDto userSetDto = TestUser.getUserSetDto2();

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.editUser("Bearer token", userSetDto));

        assertEquals(HttpStatus.BAD_REQUEST.toString() + " \"User does not exist.\"", exception.getMessage());

        verify(userRepositoryMock, times(0)).save(user1);
    }
}