package com.jetty.ssafficebe.notice.payload;

import com.jetty.ssafficebe.user.payload.UserSummary;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSummaryForList {

    private Long noticeId;
    private String title;
    private String content;
    private String createdAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String isEssentialYn;
    private String taskTypeCd;
    private UserSummary createdBy;

}
