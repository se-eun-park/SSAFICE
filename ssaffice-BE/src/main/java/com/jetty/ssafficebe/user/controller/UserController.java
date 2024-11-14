package com.jetty.ssafficebe.user.controller;

import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import com.jetty.ssafficebe.search.service.ESUserService;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UpdatePasswordRequest;
import com.jetty.ssafficebe.user.payload.UpdateUserRequest;
import com.jetty.ssafficebe.user.payload.UserFilterRequest;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.jetty.ssafficebe.user.service.UserService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ChannelService channelService;
    private final ESUserService esUserService;

    /**
     * 유저 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        ApiResponse apiResponse = userService.saveUser(saveUserRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<UserSummary> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(userService.getUserSummary(userDetails.getUserId()));
    }

    /**
     * 유저 정보 조회
     * TODO : 관리자인 경우만 허용하도록 변경
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserSummary> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserSummary(userId));
    }

    /**
     * 내 정보 수정
     */
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @RequestBody UpdateUserRequest updateUserRequest) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok().body(userService.updateUser(userDetails.getUserId(), updateUserRequest));
    }

    /**
     * 유저 정보 수정. 역할 변경, 비밀번호 변경 로직은 다른 API로 분리.
     * TODO : 관리자인 경우만 허용하도록 변경
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId,
                                                  @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok().body(userService.updateUser(userId, updateUserRequest));
    }

    /**
     * 유저 삭제. 관리자 페이지에서 사용하는 api로 유저 리스트를 받아 해당 유저 전체 soft delete. disabledYn을 'Y'로 변경.
     * TODO : 관리자인 경우만 허용하도록 변경
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUsers(@RequestBody List<Long> userIds) {
        return ResponseEntity.ok().body(userService.deleteUsers(userIds));
    }

    /**
     * 유저 리스트 조회
     * TODO : 관리자인 경우만 허용하도록 변경
     */
    @GetMapping
    public ResponseEntity<Page<UserSummary>> getUserPage(@RequestBody UserFilterRequest userFilterRequest,
                                                         @PageableDefault(
                                                                 size = 20,
                                                                 sort = "userId",
                                                                 direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(userService.getUserPage(userFilterRequest, pageable));
    }

    /**
     * 내 비밀번호 수정 비밀번호 수정 페이지에서 사용
     */
    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse> updateMyPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        return ResponseEntity.ok().body(userService.updatePassword(userDetails.getUserId(), updatePasswordRequest));
    }

    /**
     * 유저 프로필 업로드
     */
    @PostMapping("/me/profileImg")
    public ResponseEntity<ApiResponse> updateProfileImg(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @RequestParam MultipartFile profileImg) throws IOException {

        return ResponseEntity.ok().body(userService.updateProfileImg(userDetails.getUserId(), profileImg));
    }


    @GetMapping("/{userId}/channels")
    public ResponseEntity<Page<ChannelSummary>> getChannelListByUserId(@PathVariable Long userId,
                                                                       @PageableDefault(
                                                                               size = 20,
                                                                               sort = "channelId",
                                                                               direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(channelService.getChannelsByUserId(userId, pageable));
    }
}
