package com.jetty.ssafficebe.role.converter;

import com.jetty.ssafficebe.role.entity.Role;
import com.jetty.ssafficebe.role.payload.RoleDTO;
import com.jetty.ssafficebe.role.payload.RoleSummarySimple;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RoleConverter {

    RoleSummarySimple toRoleSimple(Role role);

    Role toRole(RoleDTO request);
}
