package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

    Page<NoticeSummaryForList> getNoticeList(Pageable pageable);

}
