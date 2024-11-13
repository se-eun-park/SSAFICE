package com.jetty.ssafficebe.search.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.payload.ESUserRequest;

public interface ESUserService {

    ApiResponse saveUser(ESUserRequest request);

    ApiResponse deleteUser(Long userId);
}
