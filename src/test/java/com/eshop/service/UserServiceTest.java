package com.eshop.service;

import com.eshop.dto.UserSignupDTO;
import com.eshop.model.Role;
import com.eshop.model.UserApp;
import com.eshop.repository.OrderRepository;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserAppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    FileStorageService fileStorageService;
    @Mock
    private UserAppRepository userAppRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService underTest;
    private UserSignupDTO userSingup;
    private UserApp user;

    @BeforeEach
    void setUp() {
        userAppRepository.deleteAll();
        underTest = new UserService(
                userAppRepository,
                roleRepository,
                bCryptPasswordEncoder,
                orderRepository,
                fileStorageService);
        userSingup = new UserSignupDTO();
        userSingup.setName("ali");
        userSingup.setLastName("herawi");
        userSingup.setEmail("aliherawi7@gmail.com");
        userSingup.setPassword("1234");
        userSingup.setLocation("af");
        userSingup.setImage(new byte[5]);
        user = new UserApp();
        user.setName("ali");
        user.setLastName("herawi");
        user.setEmail("aliherawi7@gmail.com");
        user.setPassword("1234");
        user.setLocation("af");
        user.setImgUrl("");
    }

    @Test
    void loadUserByUsernameIfUserAvailable() {
        //when
        String email = user.getEmail();
        when(userAppRepository.findByEmail(email)).thenReturn(user);
        //then
        assertEquals(underTest.loadUserByUsername(email).getUsername(), email);
        verify(userAppRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsernameIfUserNotAvailable() {
        //when
        String email = user.getEmail();
        when(userAppRepository.findByEmail(email)).thenReturn(null);
        //then
        assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(email));
        verify(userAppRepository).findByEmail(email);
    }

    @Test
    void addUser() {
        Role role = new Role(1, "USER");
        when(roleRepository.findByName("USER")).thenReturn(role);
        underTest.addUser(userSingup);

        //then
        ArgumentCaptor<UserApp> userAppArgumentCaptor = ArgumentCaptor.forClass(UserApp.class);
        verify(roleRepository).findByName("USER");
        verify(userAppRepository).save(userAppArgumentCaptor.capture());
        assertEquals(user.getEmail(), userAppArgumentCaptor.getValue().getEmail());
    }

    @Test
    void getUser() {
        //when
        String email = "aliherawi7@gmail.com";
        underTest.getUser(email);
        //then
        verify(userAppRepository).findByEmail(email);
    }

    @Test
        //if the user and role exists
    void addRoleToUserIfUserAndRoleExist() {
        //given
        Role role = new Role(1, "USER");
        //when
        when(userAppRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(roleRepository.findByName(role.getName())).thenReturn(role);

        underTest.addRoleToUser(user.getEmail(), role.getName());
        //then
        verify(roleRepository).findByName(role.getName());
        verify(userAppRepository).findByEmail(user.getEmail());
        verify(userAppRepository).save(user);

    }

    @Test
        //adding role if the user not exists
    void addRoleToUserIfUserAndRoleNotExist() {
        //given
        Role role = new Role(1, "USER");
        //when
        when(userAppRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(roleRepository.findByName(role.getName())).thenReturn(role);

        underTest.addRoleToUser(user.getEmail(), role.getName());
        //then
        verify(roleRepository).findByName(role.getName());
        verify(userAppRepository).findByEmail(user.getEmail());

    }

    @Test
    void getAllUsers() {
        //when
        underTest.getAllUsers();
        //then
        verify(userAppRepository).findAll();
    }

    @Test
        // if user is available and password is correct
    void deleteUserIFUserIsAvailableAndPasswordIsCorrect() {
        //given
        String email = user.getEmail();
        String password = user.getPassword();
        // when
        when(userAppRepository.findByEmail(email)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);
        underTest.deleteUser(email, password);
        //then
        verify(userAppRepository).delete(user);
    }

    @Test
        // if user is available and password is incorrect
    void deleteUserIFUserIsAvailableAndPasswordIsIncorrect() {
        //given
        String email = user.getEmail();
        String password = user.getPassword();
        // when
        when(userAppRepository.findByEmail(email)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(password, "fakePassword")).thenReturn(false);
        underTest.deleteUser(email, "fakePassword");
        //then
        assertEquals(underTest.deleteUser(email, "fakePassword"),
                new ResponseEntity<>("incorrect password!", HttpStatus.FORBIDDEN));
    }

    @Test
        // if user is available and password is incorrect
    void deleteUserIFUserIsNotAvailable() {
        //given
        String email = "fake-email";
        String password = "fakePassword";
        // when
        when(userAppRepository.findByEmail(email)).thenReturn(null);
        underTest.deleteUser(email, password);
        //then
        verify(userAppRepository).findByEmail(email);
        assertEquals(underTest.deleteUser(email, password),
                new ResponseEntity<>("user not found!", HttpStatus.NOT_FOUND));
    }

    @Test
        // check if account is lock
    void isAccountLocked() {
        // when
        user.setAccountLocked(true);
        // then
        assertTrue(underTest.isAccountLocked(user));
    }

    @Test
        // check if account is not lock
    void isNotAccountLocked() {
        // when
        user.setAccountLocked(false);
        // then
        assertFalse(underTest.isAccountLocked(user));
    }

    @Test
    void increaseFailedAttempts() {
        //when
        int failedAttempt = user.getFailedAttempt();
        when(userAppRepository.findByEmail(user.getEmail())).thenReturn(user);
        underTest.increaseFailedAttempts(user);
        // then
        verify(userAppRepository).findByEmail(user.getEmail());
        verify(userAppRepository).save(user);
        assertNotEquals(failedAttempt, user.getFailedAttempt());
    }

    @Test
    void resetFailedAttempts() {
        // given
        user.setFailedAttempt(3);
        //when
        when(userAppRepository.findByEmail(user.getEmail())).thenReturn(user);
        underTest.resetFailedAttempts(user.getEmail());

        //then
        verify(userAppRepository).findByEmail(user.getEmail());
        verify(userAppRepository).save(user);
        assertEquals(0, user.getFailedAttempt());
    }

    @Test
    void updateFailedAttempts() {

        //when
        when(userAppRepository.findByEmail(user.getEmail())).thenReturn(user);
        underTest.updateFailedAttempts(3, user.getEmail());

        //then
        verify(userAppRepository).findByEmail(user.getEmail());
        verify(userAppRepository).save(user);
        assertEquals(3, user.getFailedAttempt());
    }

    @Test
    void lock() {
        //when
        underTest.lock(user);
        //then
        verify(userAppRepository).save(user);
        assertTrue(user.isAccountLocked());
    }

    @Test
        // if user lock time has expired
    void unlockWhenTimeExpired() {
        //when
        underTest.lock(user);
        user.setLockTime(new Date(System.currentTimeMillis() - 10000));
        // then
        verify(userAppRepository).save(user);
        assertTrue(underTest.unlockWhenTimeExpired(user));
    }

    @Test
        // if user lock time has not expired
    void testIfTimeNotExpiredInUnlockWhenTimeExpiredMethod() {
        //when
        underTest.lock(user);
        // then
        verify(userAppRepository).save(user);
        assertFalse(underTest.unlockWhenTimeExpired(user));
    }
}