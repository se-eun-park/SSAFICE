package com.jetty.ssafficebe.notice.payload;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoticeFilterRequest {

    private LocalDateTime filterStartDateTime;
    private LocalDateTime filterEndDateTime;
    private String filterType; // createdAt, endDateTime
}
