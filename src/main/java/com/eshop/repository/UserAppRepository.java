package com.eshop.test.repository;

import com.eshop.test.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    boolean existsByEmail(String email);

    UserApp findByEmail(String email);

    Collection<UserApp> findByName(String name);

}
