package com.example.bookify.global.util;

import java.util.Locale;

public class KeywordUtils {

    /**
     * 특수문자 제거 + 다중 공백 제거 + trim
     */

    public static String  cleanKeyword(String rawKeyword) {

        if (rawKeyword == null)  throw new IllegalArgumentException("입력된 키워드가 null입니다.");

        // 1. 특수문자 제거 (문자, 숫자, 공백만 남김)
        String step1 = rawKeyword.replaceAll("[^\\p{L}\\p{N}\\s]", "");

        // 2. 연속된 공백을 하나로 줄임
        String step2 = step1.replaceAll("\\s+", " ");

        // 3. 앞뒤 공백 제거 및 대소문자 통일
        String step3 = step2.trim().toLowerCase(Locale.ROOT);

        // 4. 빈 문자열 체크
        if (step3.isEmpty()) {
            throw new IllegalArgumentException("정제된 키워드가 비어 있습니다.");
        }

        return step3;
    }
}
