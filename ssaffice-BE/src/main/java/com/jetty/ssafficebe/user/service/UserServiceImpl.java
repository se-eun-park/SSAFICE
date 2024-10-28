package com.jetty.ssafficebe.user.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.DuplicateValueException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.role.entity.UserRole;
import com.jetty.ssafficebe.role.repository.RoleRepository;
import com.jetty.ssafficebe.role.repository.UserRoleRepository;
import com.jetty.ssafficebe.user.converter.UserConverter;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.SaveUserRequest;
import com.jetty.ssafficebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse saveUser(SaveUserRequest saveUserRequest) {
        if (userRepository.existsByEmail(saveUserRequest.getEmail())) {
            throw new DuplicateValueException(ErrorCode.EMAIL_ALREADY_EXISTS, "email", saveUserRequest.getEmail());
        }

        // request정보로 User객체 생성.
        User user = userConverter.toUser(saveUserRequest);

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(saveUserRequest.getPassword()));
        User savedUser = userRepository.save(user);

        // 유저-역할 테이블에 유저-역할 매핑 추가. 잘못된 역할 입력 시 skip.
        for (String roleId : saveUserRequest.getRoleIds()) {
            if (roleRepository.existsById(roleId)) {
                userRoleRepository.save(UserRole.builder()
                                                .userId(savedUser.getUserId())
                                                .roleId(roleId)
                                                .build());
            }
        }

        return new ApiResponse(true, HttpStatus.CREATED, "유저 추가 성공");
    }
}
