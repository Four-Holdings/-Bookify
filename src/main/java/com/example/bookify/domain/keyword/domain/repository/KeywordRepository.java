package com.example.bookify.domain.keyword.domain.repository;

import com.example.bookify.domain.keyword.domain.model.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {

    //keyword 문자열과 DB에 저장된 keyword 컬럼 값이 같은 엔티티 찾기
    Optional<Keyword> findByKeyword(String keyword);

    //인기 검색어(Keyword)를 count 기준 내림차순으로 정렬해서 상위 N개만 가져오는 메서드
    @Query("SELECT k FROM Keyword k ORDER BY k.count DESC")
    Page<Keyword> findTopKeywords(Pageable pageable);
}
