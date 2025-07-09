package com.example.bookify.domain.review.domain.repository;

import com.example.bookify.domain.review.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 도서 ID 기준 삭제되지 않은 리뷰 조회 (필요1)
    List<Review> findBybookIdAndIsDeletedFasle(Long bookId);

}
