package com.eshop.test.repository;

import com.eshop.test.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    boolean existsByName(String name);
}
