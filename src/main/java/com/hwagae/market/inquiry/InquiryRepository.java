package com.hwagae.market.inquiry;

import com.hwagae.market.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InquiryRepository extends JpaRepository<InquiryEntity,Integer> {
    //SELECT * FROM hg.qna where user_num = '11';
    List<InquiryEntity> findAllByUserEntity(UserEntity userEntity);
    @Query("SELECT COUNT(i) FROM InquiryEntity i")
    int getTotalQnaCount();

    // 처리 완료된 신고 수를 반환하는 쿼리 메서드
    @Query("SELECT COUNT(i) FROM InquiryEntity i WHERE i.qnaStatus = 1")
    int getCompletedQnaCount();
}