package com.example.bookify.domain.statistics.domain.repository;

import com.example.bookify.domain.statistics.domain.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
