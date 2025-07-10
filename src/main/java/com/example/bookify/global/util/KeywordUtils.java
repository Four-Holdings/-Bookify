package com.example.bookify.global.util;

import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;

import java.util.Locale;

public class KeywordUtils {

    /**
     * 특수문자 제거 + 다중 공백 제거 + trim
     */

    public static String  cleanKeyword(String rawKeyword) {

        if (rawKeyword == null) throw new CustomException(ExceptionCode.INVALID_KEYWORD_NULL);


        // 1. 특수문자 제거 (문자, 숫자, 공백만 남김)
        String step1 = rawKeyword.replaceAll("[^\\p{L}\\p{N}\\s]", "");

        // 2. 연속된 공백을 하나로 줄임
        String step2 = step1.replaceAll("\\s+", " ");

        // 3. 앞뒤 공백 제거 및 대소문자 통일
        String step3 = step2.trim().toLowerCase(Locale.ROOT);

        // 4. 빈 문자열 체크
        if (step3.isEmpty()) {
            throw new CustomException(ExceptionCode.EMPTY_CLEANED_KEYWORD);
        }

        return step3;
    }
}
