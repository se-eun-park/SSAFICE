package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeFilterRequest;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface NoticeRepositoryCustom {

    List<Notice> getNoticeListByCreateUserAndFilter(Long userId,
                                                    NoticeFilterRequest noticeFilterRequest,
                                                    Sort sort);

}