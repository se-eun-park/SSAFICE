package com.jetty.ssafficebe.common.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilterRequest {

    private String filterType;
    private String startTime;
    private String endTime;
}
