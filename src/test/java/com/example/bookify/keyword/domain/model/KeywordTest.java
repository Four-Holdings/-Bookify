package com.example.bookify.keyword.domain.model;

import com.example.bookify.domain.keyword.domain.model.Keyword;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain:Model")
public class KeywordTest {

    @Test
    @DisplayName("새로운 Keyword 객체 생성")
    void create() {

        Keyword keyword = Keyword.createKeyword("자바");

        Assertions.assertThat(keyword).isNotNull();
        Assertions.assertThat(keyword.getCount()).isEqualTo(1L);
        Assertions.assertThat(keyword.getKeyword()).isEqualTo("자바");
    }

    @Test
    @DisplayName("기존 Keyword 객체 cout 1증가")
    void addCount () {

        //given
        Keyword keyword = Keyword.createKeyword("자바");

        //when
        keyword.addCount();

        //then
        Assertions.assertThat(keyword.getCount()).isEqualTo(2L);
    }
}
