package com.jetty.ssafficebe.mattermost.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jetty.ssafficebe.mattermost.payload.notifyprops.PropsSummary;
import com.jetty.ssafficebe.mattermost.payload.timezone.TimezoneSummary;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAutocompleteSummary {
	private String id;
	@JsonProperty("create_at")
	private long createAt;
	@JsonProperty("update_at")
	private long updateAt;
	@JsonProperty("delete_at")
	private long deleteAt;
	private String username;
	@JsonProperty("auth_data")
	private String authData;
	@JsonProperty("auth_service")
	private String authService;
	private String email;
	private String nickname;
	@JsonProperty("first_name")
	private String firstname;
	@JsonProperty("last_name")
	private String lastname;
	private String position;
	private String roles;
	private PropsSummary props;
	@JsonProperty("last_picture_update")
	private String lastPictureUpdate;
	private String locale;
	private TimezoneSummary timezone;
	@JsonProperty("disable_welcome_email")
	private boolean disableWelcomeEmail;

}
