package com.jetty.ssafficebe.remind.code;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum RemindAuthType {
    CREATE(ErrorCode.REMIND_CREATE_FORBIDDEN, "알림 등록 권한이 없습니다."),
    DELETE(ErrorCode.REMIND_DELETE_FORBIDDEN, "알림 삭제 권한이 없습니다.");

    private final ErrorCode errorCode;
    private final String message;

    RemindAuthType(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
