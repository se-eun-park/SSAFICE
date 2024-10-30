package com.jetty.ssafficebe.user.payload;

import com.jetty.ssafficebe.role.payload.RoleSummarySimple;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSummary {

    private Long userId;

    private String email;

    private String name;

    private String cohortNum;

    private String regionCd;

    private String classNum;

    private String trackCd;

    private String curriculumCd;

    private List<RoleSummarySimple> roles = new ArrayList<>();

    // TODO : MM 프로필 사진 가져오기가 가능하면 넘겨주기
    private String profileImage;
}
