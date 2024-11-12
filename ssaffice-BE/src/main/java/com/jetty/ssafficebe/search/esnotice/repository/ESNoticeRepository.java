package com.jetty.ssafficebe.search.esnotice.repository;

import com.jetty.ssafficebe.search.esnotice.document.ESNotice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESNoticeRepository extends ElasticsearchRepository<ESNotice, Long> {

}
