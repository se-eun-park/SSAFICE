package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.notice.entity.QNotice;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.user.entity.QUser;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.querydsl.core.types.Projections;
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
    public Page<NoticeSummaryForList> getNoticeList(Pageable pageable) {
        QNotice notice = QNotice.notice;
        QUser user = QUser.user;

        JPQLQuery<NoticeSummaryForList> query = from(notice)
                .leftJoin(notice.createdUser, user)
                .select(Projections.constructor(NoticeSummaryForList.class,
                                                notice.noticeId,
                                                notice.title,
                                                notice.content,
                                                notice.createdAt.stringValue(),
                                                notice.startDateTime,
                                                notice.endDateTime,
                                                notice.isEssentialYn,
                                                notice.taskTypeCd,
                                                Projections.constructor(UserSummary.class,
                                                                        user.userId,
                                                                        user.email,
                                                                        user.name,
                                                                        user.cohortNum.stringValue(),
                                                                        user.regionCd,
                                                                        user.classNum.stringValue(),
                                                                        user.trackCd,
                                                                        user.isDisabledYn,
                                                                        user.profileImgUrl
                                                ).as("createdBy")
                ));


        return getPageImpl(query, pageable);
    }

}
