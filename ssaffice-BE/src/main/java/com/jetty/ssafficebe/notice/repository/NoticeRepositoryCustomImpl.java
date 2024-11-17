package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.common.jpa.AbstractQueryDslRepository;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.entity.QNotice;
import com.jetty.ssafficebe.notice.payload.NoticeFilterRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class NoticeRepositoryCustomImpl extends AbstractQueryDslRepository implements NoticeRepositoryCustom {

    public NoticeRepositoryCustomImpl(JPAQueryFactory queryFactory,
                                      EntityManager entityManager) {
        super(queryFactory, entityManager);
    }


    @Override
    public List<Notice> getNoticeListByCreateUserAndFilter(Long userId, NoticeFilterRequest filter,
                                                           Sort sort) {
        QNotice notice = QNotice.notice;

        // 필터 조건 생성
        Predicate predicate = createPredicate(filter, notice);

        JPQLQuery<Notice> noticeList = from(notice)
                .where(notice.createUser.userId.eq(userId))
                .where(predicate);

        return getSortedList(noticeList, sort);
    }

    private Predicate createPredicate(NoticeFilterRequest filter, QNotice notice) {
        BooleanBuilder builder = new BooleanBuilder();

        LocalDateTime start = filter.getFilterStartDateTime();
        LocalDateTime end = filter.getFilterEndDateTime();
        String filterType = filter.getFilterType();

        if (filterType != null && start != null && end != null) {
            switch (filterType) {
                case "createdAt":
                    builder.and(notice.createdAt.between(start, end));
                    break;
                case "endDateTime":
                    builder.and(notice.endDateTime.between(start, end));
                    break;
                default:
                    throw new IllegalArgumentException("유효하지 않은 필터 타입: " + filterType);
            }
        } else if (filterType != null) {
            throw new IllegalArgumentException("필터 타입이 지정된 경우 시작 및 종료 날짜 시간이 모두 필요합니다.");
        }

        return builder;
    }

    private OrderSpecifier<?> createOrderSpecifier(Sort.Order order, QNotice notice) {
        PathBuilder<Notice> entityPath = new PathBuilder<>(Notice.class, "notice");
        try {
            // 정렬할 필드명을 기반으로 Path 생성
            return new OrderSpecifier(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    entityPath.get(order.getProperty())
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("정렬할 수 없는 필드명: " + order.getProperty(), e);
        }
    }
}
