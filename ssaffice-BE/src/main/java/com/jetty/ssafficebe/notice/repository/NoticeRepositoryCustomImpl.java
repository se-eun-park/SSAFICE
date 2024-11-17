package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class NoticeRepositoryCustomImpl extends AbstractQueryDslRepository implements NoticeRepositoryCustom {

    public NoticeRepositoryCustomImpl(JPAQueryFactory queryFactory,
                                      EntityManager entityManager) {
        super(queryFactory, entityManager);
    }


}
