package com.example.bookify.domain.review.domain.repository;

import com.example.bookify.domain.review.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 도서 ID 기준 삭제되지 않은 리뷰 조회 (필요1)
    List<Review> findBybookIdAndIsDeletedFalse(Long bookId);

    // reviewID로 단건 리뷰 조회, 삭제된 리뷰 수정 불가
    Optional<Review> findByIdAndIsDeletedFalse(Long reviewId);

}
