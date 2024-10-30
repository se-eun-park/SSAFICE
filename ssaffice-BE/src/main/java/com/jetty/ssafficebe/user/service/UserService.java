package com.jetty.ssafficebe.user.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.payload.UserInfo;

public interface UserService {

    ApiResponse saveUser(SaveUserRequest saveUserRequest);

    UserInfo getUserInfo(Long userId);
}
