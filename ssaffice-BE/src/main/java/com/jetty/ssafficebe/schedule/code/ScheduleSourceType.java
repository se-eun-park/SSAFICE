package com.jetty.ssafficebe.schedule.code;

import com.jetty.ssafficebe.commoncode.CommonCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScheduleSourceType implements CommonCode {

    GLOBAL("전체공지"),
    TEAM("팀공지"),
    ASSIGNED_TEAM("관리자팀할당"),
    ASSIGNED_PERSONAL("관리자개인할당"),
    PERSONAL("개인"),
    ;

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public String getOption() {
        return "";
    }
}
