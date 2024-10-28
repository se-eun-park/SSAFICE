package com.jetty.ssafficebe.user.repository;

import com.jetty.ssafficebe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
