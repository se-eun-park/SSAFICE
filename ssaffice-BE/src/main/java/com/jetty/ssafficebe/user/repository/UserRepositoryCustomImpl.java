package com.jetty.ssafficebe.user.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.role.entity.QUserRole;
import com.jetty.ssafficebe.user.entity.QUser;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.UserFilterRequest;
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

    @Override
    public Page<User> getUsersByFilter(UserFilterRequest userFilterRequest, Pageable pageable) {
        // 요청 받은 항목들로 필터링하여 사용자 목록을 반환하는 메소드
        QUser user = QUser.user;
        JPQLQuery<User> query = from(user);

        if (userFilterRequest.getCohortNum() != null) {
            query.where(user.cohortNum.eq(userFilterRequest.getCohortNum()));
        }
        if (userFilterRequest.getRegionCd() != null) {
            query.where(user.regionCd.eq(userFilterRequest.getRegionCd()));
        }
        if (userFilterRequest.getClassNum() != null) {
            query.where(user.classNum.eq(userFilterRequest.getClassNum()));
        }
        if (userFilterRequest.getTrackCd() != null) {
            query.where(user.trackCd.eq(userFilterRequest.getTrackCd()));
        }
        if (userFilterRequest.getDisabledYn() != null) {
            query.where(user.isDisabledYn.eq(userFilterRequest.getDisabledYn()));
        }

        return getPageImpl(query, pageable);
    }
}
