package com.example.bookify.keyword.fixture;

import com.example.bookify.domain.keyword.domain.model.Keyword;
import com.example.bookify.domain.keyword.domain.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class KeywordFixture {

    public static final String KEYWORD_NAME = "자바";

    @Autowired
    private KeywordRepository keywordRepository;

    public static Keyword generateMock() {
        return Keyword.createKeyword(KEYWORD_NAME);
    }

    public Keyword savedKeyword() {
        return keywordRepository.save(generateMock());
    }
}

