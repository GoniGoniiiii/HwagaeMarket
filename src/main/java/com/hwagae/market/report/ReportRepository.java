package com.hwagae.market.report;

import com.hwagae.market.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findAllByUserEntity(UserEntity userEntity);
    Page<ReportEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);

    @Query("SELECT COUNT(r) FROM ReportEntity r")
    int getTotalReportsCount();

    // 처리 완료된 신고 수를 반환하는 쿼리 메서드
    @Query("SELECT COUNT(r) FROM ReportEntity r WHERE r.reportState = 1")
    int getCompletedReportsCount();

    @Query("SELECT r FROM ReportEntity r WHERE r.reportSphone = :reportSphone OR r.reportSaccount = :reportSaccount OR r.reportSnick = :reportSnick")
    List<ReportEntity> findReportsByConditions(String reportSphone, String reportSaccount, String reportSnick);

}