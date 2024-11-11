package com.jetty.ssafficebe.schedule.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.schedule.entity.QSchedule;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ScheduleRepositoryCustomImpl extends AbstractQueryDslRepository implements ScheduleRepositoryCustom {

    public ScheduleRepositoryCustomImpl(JPAQueryFactory queryFactory,
                                        EntityManager entityManager) {
        super(queryFactory, entityManager);
    }

    @Override
    public Page<Schedule> getSchedulesByFilter(ScheduleFilterRequest scheduleFilterRequest, Pageable pageable) {
        QSchedule schedule = QSchedule.schedule;
        JPQLQuery<Schedule> query = from(schedule);

        // 미등록 공지 필터
        if (scheduleFilterRequest.getIsEnrollYn() != null) {
            query.where(schedule.isEnrollYn.eq(scheduleFilterRequest.getIsEnrollYn()));
        }

        // 일정 상태 필터
        if (scheduleFilterRequest.getScheduleStatusTypeCd() != null) {
            query.where(schedule.scheduleStatusTypeCd.eq(scheduleFilterRequest.getScheduleStatusTypeCd()));
        }

        // 일정 출처 필터
        if (scheduleFilterRequest.getScheduleSourceTypeCd() != null) {
            query.where(schedule.scheduleSourceTypeCd.eq(scheduleFilterRequest.getScheduleSourceTypeCd()));
        }

        // 기간 필터
        if (scheduleFilterRequest.getFilterStartDateTime() != null) {
            query.where(schedule.startDateTime.goe(scheduleFilterRequest.getFilterStartDateTime()));
        }
        if (scheduleFilterRequest.getFilterEndDateTime() != null) {
            query.where(schedule.endDateTime.loe(scheduleFilterRequest.getFilterEndDateTime()));
        }

        return getPageImpl(query, pageable);
    }
}
