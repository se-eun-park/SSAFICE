package com.jetty.ssafficebe.user.payload;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUserRequest {

    private String email;
    private String password;
    private String name;
    private List<String> roleIds = new ArrayList<>();
    private Integer cohortNum;
    private String regionCd;
    private Integer classNum;
    private String trackCd;
}
