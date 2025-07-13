package com.example.bookify.domain.keyword.domain.model;

import com.example.bookify.global.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
        name = "keywords",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_KEYWORD_TO_NAME",
                columnNames = "keyword"
        ),
        indexes = @Index(
                name = "IDX_COUNT_DESC",
                columnList = "count DESC"
        )
)

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "keyword_seq")
    @SequenceGenerator(name = "keyword_seq", sequenceName = "keyword_sequence", allocationSize = 1)
    private Long keywordId;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private Long count;

    private Keyword(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    //처음 검색어 저장할떄 사용
    // 정적 팩터리 메서드의 목적은 생성자를 무분별하게 호출하는 것을 방지하고, 해당 메서드를 통해서만 객체를 생성할 수 있도록 유도
    public static Keyword createKeyword(String keyword) {
        return new Keyword(keyword, 1L);  // 최초 저장 시 count = 1
    }

    public static Keyword createKeywordWithCount (String keyword,Long count) {
        return new Keyword(keyword, count);
    }

    //기존에 존재하는 검색어일경우 카운트 증가만
    public void addCount() {
        this.count += 1;
    }

    //캐시에 카운트 수만큼 증가
    public void incrementCountBy(long cacheCount) {
        this.count += cacheCount;
    }

}
