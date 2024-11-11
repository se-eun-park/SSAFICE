package com.jetty.ssafficebe.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 역할을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 공지사항을 찾을 수 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다."),
    REMIND_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 알림을 찾을 수 없습니다."),

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
    ROLE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 역할입니다."),
    REMIND_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 해당 시간에 알림이 존재합니다."),

    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_USAGE(HttpStatus.BAD_REQUEST, "usage 값이 잘못되었습니다."),
    INVALID_REMIND_OPERATION(HttpStatus.BAD_REQUEST, "필수 알림은 삭제할 수 없습니다."),

    REMIND_CREATE_FORBIDDEN(HttpStatus.FORBIDDEN, "알림 등록 권한이 없습니다."),
    REMIND_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "알림 삭제 권한이 없습니다."),
    SCHEDULE_CREATE_FORBIDDEN(HttpStatus.FORBIDDEN, "일정 등록 권한이 없습니다."),
    SCHEDULE_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "일정 수정 권한이 없습니다."),
    SCHEDULE_READ_FORBIDDEN(HttpStatus.FORBIDDEN, "일정 조회 권한이 없습니다."),
    SCHEDULE_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "일정 삭제 권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String msg;

    ErrorCode(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
