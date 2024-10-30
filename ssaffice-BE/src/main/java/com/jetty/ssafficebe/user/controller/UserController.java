package com.jetty.ssafficebe.user.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UserInfo;
import com.jetty.ssafficebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        ApiResponse apiResponse = userService.saveUser(saveUserRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    // TODO : 내 정보 조회 -> 로그인 구현 후 추가
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyInfo() {
        return null;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

}
