package com.jetty.ssafficebe.schedule.code;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum ScheduleAuthType {
    CREATE(ErrorCode.SCHEDULE_CREATE_FORBIDDEN, "일정 등록 권한이 없습니다."),
    UPDATE(ErrorCode.SCHEDULE_UPDATE_FORBIDDEN, "일정 수정 권한이 없습니다."),
    READ(ErrorCode.SCHEDULE_READ_FORBIDDEN, "일정 조회 권한이 없습니다."),
    DELETE(ErrorCode.SCHEDULE_DELETE_FORBIDDEN, "일정 삭제 권한이 없습니다.");

    private final ErrorCode errorCode;
    private final String message;

    ScheduleAuthType(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}


