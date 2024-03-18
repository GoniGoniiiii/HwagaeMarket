package com.hwagae.market.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Transactional
    void deleteByUserId(String userId);

    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByUserNick(String userNick);

    Optional<UserEntity> findByUserEmail(String userEmail);


    UserEntity findByUserNum(Integer userNum);

    @Query("SELECT DISTINCT u " +
            "FROM UserEntity u " +
            "JOIN ReportEntity r ON (u.userPhone = r.reportSphone OR u.userNick = r.reportSnick) " +
            "WHERE r.reportSphone = :reportSphone OR r.reportSnick = :reportSnick")
    List<UserEntity> findUsersByReportConditions(@Param("reportSphone") String reportSphone, @Param("reportSnick") String reportSnick);
}
