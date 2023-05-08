package com.eshop.repository;

public interface StatisticsRepository {
    Long countAllByYearAndMonth(int year, String month);
}
