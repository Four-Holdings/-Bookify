package com.example.bookify.keyword.domain.repository;

import static com.example.bookify.keyword.fixture.KeywordFixture.KEYWORD_NAME;
import static org.assertj.core.api.Assertions.*;


import com.example.bookify.domain.keyword.domain.model.Keyword;
import com.example.bookify.domain.keyword.domain.repository.KeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@DisplayName("Repository : Keyword")
@DataJpaTest
@Import(com.example.bookify.global.config.AuditingConfig.class)
public class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    @DisplayName("새로운 키워드 저장 Test")
    void saveNewKeyword () {

        //given
        Keyword newKeyword = Keyword.createKeyword(KEYWORD_NAME);

        //when
        keywordRepository.save(newKeyword);
        Optional<Keyword> findKeyword = keywordRepository.findByKeyword(KEYWORD_NAME);

       //then
        assertThat(findKeyword).isPresent();
        assertThat(findKeyword.get().getKeyword()).isEqualTo(KEYWORD_NAME);
        assertThat(findKeyword.get().getCount()).isEqualTo(1L);
    }
}
