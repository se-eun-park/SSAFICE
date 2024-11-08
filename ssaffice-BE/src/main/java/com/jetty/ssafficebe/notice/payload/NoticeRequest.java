package com.jetty.ssafficebe.notice.payload;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequest {

    private String mmMessageId;
    private String title;
    private String content;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String noticeTypeCd;
    private String isEssentialYn;

    // TODO : 채널 아이디로 공지 대상자 조회 후 일정 추가 기능 필요. (필수 공지, 일반 공지 나눠서 처리)
    private String channelId;

}
