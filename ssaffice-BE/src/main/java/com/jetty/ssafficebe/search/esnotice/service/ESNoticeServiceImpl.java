package com.jetty.ssafficebe.search.esnotice.service;

import com.jetty.ssafficebe.search.esnotice.repository.ESNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ESNoticeServiceImpl implements ESNoticeService {

    private final ESNoticeRepository esNoticeRepository;
}