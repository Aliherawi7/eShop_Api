package com.eshop.repository;

import com.eshop.model.UserApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserAppRepository underTest;
    private UserApp user;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        user = new UserApp(1L, "ali", "herawi", LocalDate.of(1999, 3, 29),
                "1234", "aliherawi7@gmail.com", new byte[20], "Afghanistan");

    }


    @Test
    void existsByEmail() {
        // given
        underTest.save(user);

        // when
        String email = user.getEmail();

        // then
        assertTrue(underTest.existsByEmail(email));
    }

    @Test
    void isNotExistsByEmail() {
        // given
        underTest.save(user);

        // when
        String email = "mohammad@gmail.com";

        // then
        assertFalse(underTest.existsByEmail(email));
    }

    @Test
    void findByEmail() {
        //given
        underTest.save(user);
        //when
        String email = user.getEmail();
        //then
        Assertions.assertEquals(email, underTest.findByEmail(email).getEmail());
    }

    @Test
    void itShouldNotFindByEmail() {
        //given
        underTest.save(user);
        //when
        String email = "someone@gmail.com";
        //then
        assertNull(underTest.findByEmail(email));
    }

    @Test
    void findByName() {
        // given
        user.setName("ahmad");
        underTest.save(user);

        // when
        String name = user.getName();

        // then
        assertTrue(underTest.findByName(name).stream().allMatch(item -> item.getName().equals(name)));
    }

    @Test
    void itShouldNotFindByName() {
        // given
        user.setName("ahmad");
        underTest.save(user);

        // when
        String name = "John";

        // then
        if (underTest.findByName(name).size() > 0) {
            assertFalse(underTest.findByName(name).stream().allMatch(item -> item.getName().equals(name)));
        } else {
            assertFalse(Boolean.FALSE);
        }

    }
}