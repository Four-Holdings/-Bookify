package com.example.bookify.domain.keyword.domain.model;

import com.example.bookify.global.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "keywords")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(nullable = false,unique = true)
    private String keyword;

    @Column(nullable = false)
    private Long count;

    // 생성자 (엔티티 내부용)
    public Keyword(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    //처음 검색어 저장할떄 사용
    public static Keyword createKeyword (String keyword) {
        return new Keyword(keyword, 1L);  // 최초 저장 시 count = 1
    }

    //기존에 존재하는 검색어일경우 카운트 증가만
    public void addCount() {
        this.count += 1;
    }
}
