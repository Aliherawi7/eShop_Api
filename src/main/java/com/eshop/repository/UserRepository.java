package com.eshop.repository;

import com.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    Collection<User> findByName(String name);

}
