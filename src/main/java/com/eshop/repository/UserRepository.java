package com.eshop.repository;

import com.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    Collection<User> findByName(String name);

    @Query("Update User u set u.failedAttempt = ?1 WHERE u.email = ?2")
    void updateFailedAttempts(int failed, String email);

}
