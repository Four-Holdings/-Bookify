package com.example.bookify.global.util;

import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;

import java.util.Locale;

import static org.apache.logging.log4j.util.Strings.EMPTY;

public class StringUtils {

    /**
     * 특수문자 제거 + 다중 공백 제거 + trim
     */
    private static final String symbolsRegexPatterns = "[^\\p{L}\\p{N}\\s]";
    private static final String consecutiveSpaces = "\\s+";

    public static String cleanString(String rawString) {
        if (rawString == null) throw new CustomException(ExceptionCode.INVALID_KEYWORD_NULL);

        // 1. 특수문자 제거 (문자, 숫자, 공백만 남김)
        String step1 = rawString.replaceAll(symbolsRegexPatterns, EMPTY);

        // 2. 연속된 공백을 하나로 줄임
        String step2 = step1.replaceAll(consecutiveSpaces, EMPTY);

        // 3. 앞뒤 공백 제거 및 대소문자 통일
        String step3 = step2.trim().toLowerCase(Locale.ROOT);

        // 4. 빈 문자열 체크
        validateEmptyString(step3);

        return step3;
    }

    private static void validateEmptyString(String step3) {
        if (step3.isEmpty()) {
            throw new CustomException(ExceptionCode.EMPTY_CLEANED_KEYWORD);
        }
    }
}
