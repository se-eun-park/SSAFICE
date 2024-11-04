package com.jetty.ssafficebe.schedule.entity;

import com.jetty.ssafficebe.commoncode.CommonCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScheduleSourceType implements CommonCode {

    NOTICE("공지"),
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
