package com.jetty.ssafficebe.mattermost.payload.timezone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimezoneSummary {
	@JsonProperty("automaticTimezone")
	private String automaticTimezone;
	@JsonProperty("manualTimezone")
	private String manualTimezone;
	@JsonProperty("useAutomaticTimezone")
	private String useAutomaticTimezone;

}
