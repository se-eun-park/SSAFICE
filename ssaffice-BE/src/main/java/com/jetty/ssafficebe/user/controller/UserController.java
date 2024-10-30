package com.jetty.ssafficebe.user.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UpdateUserRequest;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.jetty.ssafficebe.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
     * @param userId            유저 ID
     * @param updateUserRequest 수정할 유저 정보
     * @return ApiResponse
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId,
                                                  @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok().body(userService.updateUser(userId, updateUserRequest));
    }

    /**
     * 유저 삭제. 관리자 페이지에서 사용하는 api로 유저 리스트를 받아 해당 유저 전체 soft delete.
     * disabledYn을 'Y'로 변경.
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUsers(@RequestBody List<Long> userIds) {
        return ResponseEntity.ok().body(userService.deleteUsers(userIds));
    }

}
