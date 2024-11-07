package com.jetty.ssafficebe.common.code;

import com.jetty.ssafficebe.commoncode.CommonCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TaskType implements CommonCode {

    GENERAL("일반"),
    FILE("파일"),
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
