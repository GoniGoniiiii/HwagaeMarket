package com.hwagae.market;


import com.hwagae.market.inquiry.InquiryEntity;
import com.hwagae.market.report.ReportEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationNum;

    @Column // 알림 받는 사용자의 번호
    private int userNum;

    @Column //알림 받는 사용자 아이디
    private String userId;

    @Column//알림 메세지
    private String message;

    @Column //알림을 보낸 시간
    private Timestamp notificationDate;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="qna_num")
    private InquiryEntity qna;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="report_num")
    private ReportEntity report;

}
