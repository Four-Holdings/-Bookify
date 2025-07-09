package com.example.bookify.global.common.apiresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"message", "data", "timestamp"})
public class ResponseDto<T> {

    private String message;

    private T data;

    private LocalDateTime timestamp;

    public ResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
