package com.jetty.ssafficebe.sso.controller;

import com.jetty.ssafficebe.auth.payload.AuthenticationResponse;
import com.jetty.ssafficebe.common.security.jwt.JwtProvider;
import com.jetty.ssafficebe.sso.factory.SsoServiceFactory;
import com.jetty.ssafficebe.sso.payload.SsoAuthToken;
import com.jetty.ssafficebe.sso.service.SsoService;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.UserRequestForSso;
import com.jetty.ssafficebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sso")
@RequiredArgsConstructor
public class SsoController {

    private final SsoServiceFactory ssoServiceFactory;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    /**
     * SSO 인증 요청 URL 반환 현재는 SSAFY SSO만 지원
     */
    @GetMapping("/providers/{provider}/authorization-uri")
    public String redirectAuthCodeRequestUrl(@PathVariable String provider) {
        SsoService ssoService = ssoServiceFactory.getSsoService(provider);
        return ssoService.getAuthCodeRequestUrl();
    }

    /**
     * SSO 로그인 현재는 SSAFY 로그인만 지원
     */
    @PostMapping("/providers/{provider}/login")
    public AuthenticationResponse ssoLogin(@PathVariable String provider, @RequestBody String code) {
        SsoService ssoService = ssoServiceFactory.getSsoService(provider);
        SsoAuthToken ssoAuthToken = ssoService.getSsoAuthToken(code);
        UserRequestForSso userRequest = ssoService.getLoginUserInfo(ssoAuthToken);
        String loginId = this.userService.handleSsoLogin(userRequest);

        if (loginId == null) {
            // isDisabled가 false인 유저 객체 생성 후 SSO Id만 set하여 저장
            User user = this.userService.saveUserForSSO(userRequest.getUserId());
            AuthenticationResponse.builder().userId(user.getUserId()).isSuccess(false).build();
        }

        return AuthenticationResponse.builder().jwt(jwtProvider.generateToken(loginId)).isSuccess(true).build();
    }

}