package com.example.bookify.keyword.domain.model;

import com.example.bookify.domain.keyword.domain.model.Keyword;
import com.example.bookify.keyword.fixture.KeywordFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.bookify.keyword.fixture.KeywordFixture.KEYWORD_NAME;


@DisplayName("Domain:Model")
public class KeywordTest { ;

    @Test
    @DisplayName("새로운 Keyword 객체 생성")
    void create() {

        //when
        Keyword newKeyword = KeywordFixture.generateMock();

        //then
        Assertions.assertThat(newKeyword).isNotNull();
        Assertions.assertThat(newKeyword.getCount()).isEqualTo(1L);
        Assertions.assertThat(newKeyword.getKeyword()).isEqualTo(KEYWORD_NAME);
    }

    @Test
    @DisplayName("기존 Keyword 객체 cout 1증가")
    void addCount () {

        //given
        Keyword keyword = Keyword.createKeyword(KEYWORD_NAME);

        //when
        keyword.addCount();

        //then
        Assertions.assertThat(keyword.getCount()).isEqualTo(2L);
    }
}
