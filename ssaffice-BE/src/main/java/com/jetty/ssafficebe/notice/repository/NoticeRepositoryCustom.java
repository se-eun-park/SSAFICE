package com.jetty.ssafficebe.notice.repository;

import com.jetty.ssafficebe.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {

    Page<Notice> getNoticePage(Long userId, Pageable pageable);

    Page<Notice> getNoticePageForAdmin(Long userId, Pageable pageable);

}
