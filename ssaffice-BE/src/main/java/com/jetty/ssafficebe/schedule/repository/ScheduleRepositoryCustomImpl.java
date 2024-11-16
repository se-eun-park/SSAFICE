package com.jetty.ssafficebe.schedule.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.schedule.code.ScheduleStatusType;
import com.jetty.ssafficebe.schedule.entity.QSchedule;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ScheduleRepositoryCustomImpl extends AbstractQueryDslRepository implements ScheduleRepositoryCustom {

    public ScheduleRepositoryCustomImpl(JPAQueryFactory queryFactory,
                                        EntityManager entityManager) {
        super(queryFactory, entityManager);
    }

    // 기본 페이징 조회
    @Override
    public Page<Schedule> findSchedulesByUserIdAndFilter(Long userId, ScheduleFilterRequest filterRequest, Pageable pageable) {
        QSchedule schedule = QSchedule.schedule;

        JPQLQuery<Schedule> query = from(schedule)
                .where(schedule.createUser.userId.eq(userId));

        applyFilterConditions(query, filterRequest);

        return getPageImpl(query, pageable);
    }

    // 공지사항 일정 조회
    @Override
    public Page<Schedule> findSchedulesByNoticeIdAndFilter(Long noticeId, ScheduleFilterRequest filterRequest, Pageable pageable) {
        QSchedule schedule = QSchedule.schedule;

        JPQLQuery<Schedule> query = from(schedule)
                .where(schedule.noticeId.eq(noticeId));

        applyFilterConditions(query, filterRequest);

        return getPageImpl(query, pageable);
    }

    // 해야할일/진행중/완료 카운트
    @Override
    public List<Long> getStatusCounts(List<Schedule> schedules) {
        // 필터링된 일정 리스트에서 상태별 카운트
        long todoCount = schedules.stream()
                                  .filter(s -> ScheduleStatusType.TODO.equals(s.getScheduleStatusType()))
                                  .count();

        long inProgressCount = schedules.stream()
                                        .filter(s -> ScheduleStatusType.IN_PROGRESS.equals(s.getScheduleStatusType()))
                                        .count();

        long doneCount = schedules.stream()
                                  .filter(s -> ScheduleStatusType.DONE.equals(s.getScheduleStatusType()))
                                  .count();

        return Arrays.asList(todoCount, inProgressCount, doneCount);
    }

    // 등록 true+완료 / 등록 true 카운트
    @Override
    public List<Long> getCompletionCounts(List<Schedule> schedules) {
        // 등록된 일정 수
        long enrolledCount = schedules.stream()
                                      .filter(Schedule::getIsEnroll)
                                      .count();

        // 완료된 일정 수
        long completedCount = schedules.stream()
                                       .filter(Schedule::getIsEnroll)
                                       .filter(s -> ScheduleStatusType.DONE.equals(s.getScheduleStatusType()))
                                       .count();

        return Arrays.asList(enrolledCount, completedCount);
    }

    // 필터 처리
    private void applyFilterConditions(JPQLQuery<?> query, ScheduleFilterRequest filterRequest) {
        QSchedule schedule = QSchedule.schedule;

        if (filterRequest.getIsEnrollYn() != null) {
            query.where(schedule.isEnrollYn.eq(filterRequest.getIsEnrollYn()));
        }
        if (filterRequest.getScheduleSourceTypeCd() != null) {
            query.where(schedule.scheduleSourceTypeCd.eq(filterRequest.getScheduleSourceTypeCd()));
        }
        if (filterRequest.getScheduleStatusTypeCd() != null) {
            query.where(schedule.scheduleStatusTypeCd.eq(filterRequest.getScheduleStatusTypeCd()));
        }
        if (filterRequest.getFilterStartDateTime() != null) {
            query.where(schedule.startDateTime.goe(filterRequest.getFilterStartDateTime()));
        }
        if (filterRequest.getFilterEndDateTime() != null) {
            query.where(schedule.endDateTime.loe(filterRequest.getFilterEndDateTime()));
        }
    }
}
