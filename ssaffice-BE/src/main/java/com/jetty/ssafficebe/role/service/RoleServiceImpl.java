package com.jetty.ssafficebe.role.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.utils.CollectionUtil;
import com.jetty.ssafficebe.role.converter.RoleConverter;
import com.jetty.ssafficebe.role.entity.Role;
import com.jetty.ssafficebe.role.entity.UserRole;
import com.jetty.ssafficebe.role.payload.RoleAssignmentRequest;
import com.jetty.ssafficebe.role.payload.RoleSummarySimple;
import com.jetty.ssafficebe.role.repository.RoleRepository;
import com.jetty.ssafficebe.role.repository.UserRoleRepository;
import com.jetty.ssafficebe.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleConverter roleConverter;

    @Override
    public List<RoleSummarySimple> getRoleList() {
        return CollectionUtil.transform(this.roleRepository.findAll(), this.roleConverter::toRoleSimple);
    }

    @Override
    @Transactional
    public ApiResponse assignRoleToUsers(String roleId, RoleAssignmentRequest request) {
        Role role;
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException(ErrorCode.ROLE_NOT_FOUND, "roleId", roleId);
        }
        role = this.roleRepository.getReferenceById(roleId);

        CollectionUtil.deleteOrUpdateOrInsert(
                role.getUserRoles(),                // 원본: 현재 역할에 할당된 사용자 목록
                request.getUserIds(),             // 업데이트 대상: 요청으로 들어온 사용자 ID 목록
                UserRole::getUserId,              // 원본 Key 함수: UserRole의 userId
                Function.identity(),              // 업데이트 대상 Key 함수: 단순한 userId
                null, // 삭제 함수
                null, // 업데이트 함수 (필요 없으면 비워 둠)
                userId -> {
                    if (this.userRepository.existsById(userId)) {
                        UserRole userRole = new UserRole();
                        userRole.setRoleId(roleId);
                        userRole.setUserId(userId);
                        role.getUserRoles().add(userRole);
                        return userRole;
                    } else {
                        throw new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId);
                    }
                }
        );

        return new ApiResponse(true, HttpStatus.CREATED, "역할 할당이 완료되었습니다.", role.getRoleId());
    }


}
