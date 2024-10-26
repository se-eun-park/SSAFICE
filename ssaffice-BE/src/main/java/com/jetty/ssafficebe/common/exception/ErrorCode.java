package com.jetty.ssafficebe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 역할을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_ROLE(HttpStatus.CONFLICT, "이미 등록되어있는 역할입니다."),
    ;

    private final HttpStatus status;
    private final String msg;

    ErrorCode(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
