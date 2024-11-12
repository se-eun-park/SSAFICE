package com.jetty.ssafficebe.search.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ESUserSearchRequest {

    private String Keyword;
    private String channelId;
}
