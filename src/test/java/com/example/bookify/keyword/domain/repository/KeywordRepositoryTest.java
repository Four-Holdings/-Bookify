package com.example.bookify.keyword.domain.repository;

import static org.assertj.core.api.Assertions.*;


import com.example.bookify.domain.keyword.domain.model.Keyword;
import com.example.bookify.domain.keyword.domain.repository.KeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@DisplayName("Repository : Keyword")
@DataJpaTest
public class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    @DisplayName("새로운 키워드 저장 Test")
    void saveNewKeyword () {

        //given
        Keyword Newkeyword = Keyword.createKeyword("자바");

        //when
        keywordRepository.save(Newkeyword);
        Optional<Keyword> findKeyword = keywordRepository.findByKeyword("자바");

       //then
        assertThat(findKeyword).isPresent();
        assertThat(findKeyword.get().getKeyword()).isEqualTo("자바");
        assertThat(findKeyword.get().getCount()).isEqualTo(1L);
    }
}
