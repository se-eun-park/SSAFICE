package com.jetty.ssafficebe.mattermost.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectMessageRequest {
  private Long scheduleId;
  private Long targetUserId;
}
