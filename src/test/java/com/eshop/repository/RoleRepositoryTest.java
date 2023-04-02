package com.eshop.repository;

import com.eshop.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository underTest;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(1, "ADMIN");
    }

    @Test
    void findByName() {
        //given

        underTest.save(role);
        //when
        String name = role.getName();
        // then
        Assertions.assertEquals(name, underTest.findByName(name).getName());
    }

    @Test
    void existsByName() {
        //given
        underTest.save(role);
        //when
        String name = role.getName();
        //then
        assertTrue(underTest.existsByName(name));
    }

    @Test
    void itDoesNotExistsByName() {
        //given
        underTest.save(role);
        //when
        String name = "USER";
        //then
        assertFalse(underTest.existsByName(name));
    }
}