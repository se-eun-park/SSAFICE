package com.jetty.ssafficebe.mattermost.payload.notifyprops;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropsSummary {

	private CustomStatusSummary customStatus;
	@JsonProperty("last_search_pointer")
	private String lastSearchPointer;
}
