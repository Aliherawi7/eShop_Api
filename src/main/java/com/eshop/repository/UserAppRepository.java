package com.eshop.repository;

import com.eshop.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserAppRepository extends JpaRepository<UserApp, Long>, StatisticsRepository {
    boolean existsByEmail(String email);

    UserApp findByEmail(String email);

    Collection<UserApp> findByName(String name);

    @Override
    @Query("select count(u.email) from UserApp u where function('Year', u.joinedDate) = :year and function('MONTHNAME', u.joinedDate) like :month")
    Long countAllByYearAndMonth(int year, String month);
}
