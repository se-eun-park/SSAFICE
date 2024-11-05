package com.jetty.ssafficebe.role.repository;

import com.jetty.ssafficebe.role.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    boolean existsByUserIdAndRoleId(Long userId, String roleId);
}
