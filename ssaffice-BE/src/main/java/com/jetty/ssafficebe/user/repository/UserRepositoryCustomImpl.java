package com.jetty.ssafficebe.user.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.role.entity.QUserRole;
import com.jetty.ssafficebe.user.entity.QUser;
import com.jetty.ssafficebe.user.entity.User;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserRepositoryCustomImpl extends AbstractQueryDslRepository implements UserRepositoryCustom {

    public UserRepositoryCustomImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        super(queryFactory, entityManager);
    }

    @Override
    public Page<User> findUsersByRoleId(String roleId, Pageable pageable) {
        QUser user = QUser.user;
        QUserRole userRole = QUserRole.userRole;

        JPQLQuery<User> query = from(user).join(user.userRoles, userRole)
                                          .where(userRole.roleId.eq(roleId))
                                          .select(user);

        return getPageImpl(query, pageable);
    }
}
