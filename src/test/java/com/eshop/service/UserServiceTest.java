package com.eshop.service;

import com.eshop.model.UserApp;
import com.eshop.repository.UserAppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoAnnotations.class)
class UserServiceTest {
    @Mock
    private UserAppRepository userAppRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void addUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void addRoleToUser() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void isAccountLocked() {
    }

    @Test
    void increaseFailedAttempts() {
    }

    @Test
    void resetFailedAttempts() {
    }

    @Test
    void updateFailedAttempts() {
    }

    @Test
    void lock() {
    }

    @Test
    void unlockWhenTimeExpired() {
    }
}