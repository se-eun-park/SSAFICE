package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.entity.QNotice;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class NoticeRepositoryCustomImpl extends AbstractQueryDslRepository implements NoticeRepositoryCustom {

    public NoticeRepositoryCustomImpl(JPAQueryFactory queryFactory,
                                      EntityManager entityManager) {
        super(queryFactory, entityManager);
    }

    @Override
    public Page<Notice> getNoticeList(Pageable pageable) {
        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> query = from(notice);

        return getPageImpl(query, pageable);
    }


}
