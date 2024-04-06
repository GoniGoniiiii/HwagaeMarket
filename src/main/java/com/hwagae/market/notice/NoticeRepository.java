package com.hwagae.market.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    public List<NoticeEntity> findAllByOrderByNoticeNumDesc();
}