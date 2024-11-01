package com.jetty.ssafficebe.auth.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {

    private String jwt;
}
