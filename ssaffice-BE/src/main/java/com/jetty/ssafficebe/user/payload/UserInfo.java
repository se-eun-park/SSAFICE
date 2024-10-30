package com.jetty.ssafficebe.user.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    private Long userId;

    private String email;

    private String name;

    private String cohortNum;

    private String regionCd;

    private String classNum;

    private String trackCd;

    // TODO : MM 프로필 사진 가져오기가 가능하면 넘겨주기
    private String profileImage;


}
