package com.jetty.ssafficebe.schedule.payload;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemindResponse {

    private Long remindId;
    private String isEssentialYn;
    private LocalDateTime remindDateTime;
}
