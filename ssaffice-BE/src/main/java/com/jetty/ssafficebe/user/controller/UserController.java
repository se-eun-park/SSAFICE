package com.jetty.ssafficebe.user.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.user.payload.UpdateUserRequest;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.jetty.ssafficebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<UserSummary> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserSummary(userId));
    }

    /**
     * 유저 정보 수정. 역할 변경, 비밀번호 변경 로직은 다른 API로 분리.
     *
     * @param userId 유저 ID
     * @param updateUserRequest 수정할 유저 정보
     * @return ApiResponse
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
        ApiResponse apiResponse = userService.updateUser(userId, updateUserRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
