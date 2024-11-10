package com.jetty.ssafficebe.remind.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.remind.entity.QRemind;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class RemindRepositoryCustomImpl extends AbstractQueryDslRepository implements RemindRepositoryCustom {

    public RemindRepositoryCustomImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        super(queryFactory, entityManager);
    }

    @Override
    public boolean existsDuplicateRemind(Long scheduleId, String remindTypeCd, LocalDateTime remindDateTime) {
        QRemind remind = QRemind.remind;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(remind.scheduleId.eq(scheduleId));

        if ("DAILY".equals(remindTypeCd)) {
            // DAILY: 같은 시간대 체크
            builder.and(remind.remindTypeCd.eq("DAILY"))
                   .and(remind.remindDateTime.hour().eq(remindDateTime.getHour()))
                   .and(remind.remindDateTime.minute().eq(remindDateTime.getMinute()));
        } else {
            // ONCE: 매일 알림 or 같은 시점 알림 체크
            builder.and(
                    remind.remindTypeCd.eq("DAILY").and(
                                  remind.remindDateTime.hour().eq(remindDateTime.getHour())
                                                       .and(remind.remindDateTime.minute().eq(remindDateTime.getMinute()))
                          )
                                       .or(
                                               remind.remindTypeCd.eq("ONCE")
                                                                  .and(remind.remindDateTime.eq(remindDateTime))
                                       )
            );
        }

        return queryFactory
                .selectOne()
                .from(remind)
                .where(builder)
                .fetchFirst() != null;
    }
}
