package com.jetty.ssafficebe.user.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;

    private String cohortNum;

    private String trackCd;

    private String regionCd;

    private String curriculumCd;

    private String classNum;
}
