package com.jetty.ssafficebe.notice.payload;

import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeSummaryForList {

    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String isEssentialYn;
    private String taskTypeCd;
    private CreatedBySummary createUser;

}
