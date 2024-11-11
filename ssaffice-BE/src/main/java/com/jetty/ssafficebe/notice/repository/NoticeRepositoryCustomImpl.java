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
    public Page<Notice> getNoticeList(Long userId, Pageable pageable) {
        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> query = from(notice);

        // 1. 유저 아이디로 채널 리스트 조회
        // 2. 채널 리스트로 공지 리스트 조회
        // 3. 최신순으로 정렬하여 리턴

        return getPageImpl(query, pageable);
    }

    @Override
    public Page<Notice> getNoticeListForAdmin(Long userId, Pageable pageable) {
        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> query = from(notice)
                .where(
                        notice.noticeTypeCd.eq("GLOBAL")
                                           .or(notice.noticeTypeCd.eq("TEAM").and(notice.createdBy.eq(userId)))

                );

        return getPageImpl(query, pageable);
    }


}
