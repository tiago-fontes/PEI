package com.peiload.ridecare.user.service;

import com.peiload.ridecare.common.JwtTokenUtil;
import com.peiload.ridecare.user.dto.UserSetDto;
import com.peiload.ridecare.user.dto.UserShowDto;
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

import static com.peiload.ridecare.user.model.TestUser.getUser1;
import static com.peiload.ridecare.user.model.TestUser.getUser2;
import static com.peiload.ridecare.user.model.TestUser.getUserSetDto1;
import static com.peiload.ridecare.user.model.TestUser.getUserSetDto2;
import static com.peiload.ridecare.user.model.TestUser.getUsersList;
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
        User user1 = getUser1();
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

        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    }

    @Test
    void getUser_happyPath() {
        User user1 = getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        UserShowDto result = testObj.getUser("Bearer token");

        assertNotNull(result);
        assertEquals(user1.getEmail(), result.getEmail());
        assertEquals(user1.getCompanyName(), result.getCompanyName());
    }

    @Test
    void getAllUsers_happyPath() {
        List<User> userList = getUsersList();
        when(userRepositoryMock.findAll()).thenReturn(userList);

        List<UserShowDto> result = testObj.getAllUsers();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    void createUser_happyPath() {
        UserSetDto user1 = getUserSetDto1();
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
                    assertThat(newUser.getEmail()).isEqualTo(user1.getEmail());
                    assertThat(newUser.getCompanyName()).isEqualTo(user1.getCompanyName());
                    assertThat(newUser.getPassword()).isEqualTo(user1.getPassword());
                    return true;
                })
        );
    }

    @Test
    void createUser_whenEmailAlreadyExists_shouldRaiseAnException() {
        User user1 = getUser1();
        UserSetDto userSetDto1 = getUserSetDto1();
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.createUser(userSetDto1));

        assertEquals(HttpStatus.FORBIDDEN.toString(), exception.getMessage());
    }

    @Test
    void deleteUser_happyPath() {
        User user1 = getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        testObj.deleteUser("Bearer token");

        verify(userRepositoryMock, times(1)).delete(
                argThat(userToDelete -> {
                    assertThat(userToDelete).isNotNull();
                    assertThat(userToDelete.getId()).isEqualTo(user1.getId());
                    assertThat(userToDelete.getEmail()).isEqualTo(user1.getEmail());
                    assertThat(userToDelete.getCompanyName()).isEqualTo(user1.getCompanyName());
                    assertThat(userToDelete.getPassword()).isEqualTo(user1.getPassword());
                    return true;
                })
        );
    }

    @Test
    void deleteUser_whenUserDoesntExist_shouldRaiseAnException() {
        when(userRepositoryMock.findByEmail(any())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.deleteUser("Bearer token"));

        assertEquals(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    }

    @Test
    void editUser_happyPath(){
        User user1 = getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findById(user1.getId())).thenReturn(Optional.of(user1));

        UserSetDto userSetDto = getUserSetDto2();

        testObj.editUser("Bearer token", user1.getId(), userSetDto);

        verify(userRepositoryMock, times(1)).save(
                argThat(u -> {
                    assertThat(u).isNotNull();
                    assertThat(u.getEmail()).isEqualTo(userSetDto.getEmail());
                    assertThat(u.getCompanyName()).isEqualTo(userSetDto.getCompanyName());
                    return true;
                })
        );
    }

    @Test
    void editUser_whenUserTryingToEditIsNotTheOwner_shouldRaiseAnException(){
        User user1 = getUser1();
        User user2 = getUser2();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findById(user2.getId())).thenReturn(Optional.of(user2));

        UserSetDto userSetDto = getUserSetDto2();

        int userId2 = user2.getId();
        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.editUser("Bearer token", userId2, userSetDto));

        assertEquals(HttpStatus.FORBIDDEN.toString() + " \"The profile you are trying to edit belongs to another user\"", exception.getMessage());

        verify(userRepositoryMock, times(0)).save(user1);
    }

    @Test
    void editUser_whenUserDoesntExist_shouldRaiseAnException(){
        User user1 = getUser1();
        when(jwtTokenUtilMock.getEmailFromAuthorizationString("Bearer token")).thenReturn(user1.getEmail());
        when(userRepositoryMock.findById(user1.getId())).thenReturn(Optional.ofNullable(null));

        UserSetDto userSetDto = getUserSetDto2();

        int userId = user1.getId();
        Throwable exception = assertThrows(ResponseStatusException.class, () -> testObj.editUser("Bearer token", userId, userSetDto));

        assertEquals(HttpStatus.FORBIDDEN.toString() + " \"There's no user with this id\"", exception.getMessage());

        verify(userRepositoryMock, times(0)).save(user1);
    }
}