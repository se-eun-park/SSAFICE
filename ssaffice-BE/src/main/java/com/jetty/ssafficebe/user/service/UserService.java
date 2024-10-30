package com.jetty.ssafficebe.user.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UpdateUserRequest;
import com.jetty.ssafficebe.user.payload.UserSummary;

public interface UserService {

    ApiResponse saveUser(SaveUserRequest saveUserRequest);

    UserSummary getUserSummary(Long userId);

    ApiResponse updateUser(Long userId, UpdateUserRequest saveUserRequest);
}
