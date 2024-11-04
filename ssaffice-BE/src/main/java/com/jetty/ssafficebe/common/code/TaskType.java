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
        return "";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public String getOption() {
        return "";
    }
}
