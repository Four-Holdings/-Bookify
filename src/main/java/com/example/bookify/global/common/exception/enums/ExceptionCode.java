package com.example.bookify.global.common.exception.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    // 400 Bad Request
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 최소 4자 이상이어야 합니다."),
    INVALID_BOOK_NAME_FORMAT(HttpStatus.BAD_REQUEST, "도서 이름은 최소 1자리 이상입니다."),
    INVALID_RATING_VALUE(HttpStatus.BAD_REQUEST, "평점은 1 ~ 5 사이어야 합니다."),
    MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST, "필수 입력값이 누락되었습니다."),
    EMPTY_UPDATE_CONTENT(HttpStatus.BAD_REQUEST, "내용을 입력해야 합니다."),
    INVALID_LIMIT_VALUE(HttpStatus.BAD_REQUEST, "limit 값은 0 이상이어야 합니다."),
    INVALID_KEYWORD_NULL(HttpStatus.BAD_REQUEST, "입력된 키워드가 null입니다."),
    EMPTY_CLEANED_KEYWORD(HttpStatus.BAD_REQUEST, "정제된 키워드가 비어 있습니다."),

    // 401 Unauthorized
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_OR_MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "Access token이 유효하지 않거나 존재하지 않습니다."),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 이메일, 혹은 비밀번호입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰 정보입니다."),

    // 403 Forbidden
    FORBIDDEN_RETURN_BOOK(HttpStatus.FORBIDDEN, "도서 반납에 대한 권한이 없습니다."),
    FORBIDDEN_RETURN_REVIEW(HttpStatus.FORBIDDEN, "리뷰 삭제에 대한 권한이 없습니다."),
    FORBIDDEN_UPDATE_REVIEW(HttpStatus.FORBIDDEN, "리뷰 수정에 대한 권한이 없습니다."),
    FORBIDDEN_DELETE_REVIEW(HttpStatus.FORBIDDEN, "리뷰 삭제에 대한 권한이 없습니다."),

    // 404 Not Found
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND, "도서를 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, " 리뷰를 찾을 수 없습니다."),
    NOT_FOUND_PAGE(HttpStatus.NOT_FOUND, "존재하지 않는 페이지입니다."),


    // 409 Conflict
    EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    EXISTS_NAME(HttpStatus.CONFLICT, "이미 존재하는 이름입니다."),
    EXISTS_BOOK(HttpStatus.CONFLICT, "이미 존재하는 도서입니다."),
    RENTED_BOOK(HttpStatus.CONFLICT, "이미 대여중인 도서입니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
