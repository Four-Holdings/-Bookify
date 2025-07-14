package com.example.bookify.domain.statistics.domain.repository;

import com.example.bookify.domain.statistics.domain.model.Statistics;
import com.example.bookify.domain.statistics.service.dto.AvgReviewGradeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("SELECT s FROM Statistics s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    Page<Statistics> findTopRating(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   Pageable pageable
    );

    @Query("SELECT new com.example.bookify.domain.statistics.service.dto.AvgReviewGradeDto(b, AVG (r.grades), COUNT (r)) " +
            "FROM Review r " +
            "JOIN r.book b " +
            "WHERE r.isDeleted = false AND b.isDeleted = false AND r.updatedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY b " +
            "ORDER BY AVG (r.grades) DESC, COUNT (r) DESC")
    List<AvgReviewGradeDto> avgByReviewByGrades(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate
    );

    @Modifying
    @Query("DELETE FROM Statistics s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    void deleteByStatistics(@Param("startDate") LocalDateTime startDate,
                            @Param("endDate") LocalDateTime endDate
    );

}
