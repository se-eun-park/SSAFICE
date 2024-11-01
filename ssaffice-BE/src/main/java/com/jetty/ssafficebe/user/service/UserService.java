package com.jetty.ssafficebe.user.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UpdatePasswordRequest;
import com.jetty.ssafficebe.user.payload.UpdateUserRequest;
import com.jetty.ssafficebe.user.payload.UserFilterRequest;
import com.jetty.ssafficebe.user.payload.UserSummary;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    ApiResponse saveUser(SaveUserRequest saveUserRequest);

    UserSummary getUserSummary(Long userId);

    ApiResponse updateUser(Long userId, UpdateUserRequest saveUserRequest);

    ApiResponse deleteUsers(List<Long> userIds);

    Page<UserSummary> getUserPage(UserFilterRequest userFilterRequest, Pageable pageable);

    ApiResponse updatePassword(Long userId, UpdatePasswordRequest updatePasswordRequest);
}
