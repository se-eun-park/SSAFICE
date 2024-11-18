package com.jetty.ssafficebe.mattermost.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MMChannelSummary {
    private String id;
    private String teamId;
    private String type;
    private String displayName;
}
