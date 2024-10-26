package com.jetty.ssafficebe.role.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.role.payload.RoleAssignmentRequest;
import com.jetty.ssafficebe.role.payload.RoleSummarySimple;
import java.util.List;

public interface RoleService {

    List<RoleSummarySimple> getRoleList();

    ApiResponse assignRoleToUsers(String roleId, RoleAssignmentRequest request);
}
