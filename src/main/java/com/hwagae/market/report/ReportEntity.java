package com.hwagae.market.report;

import com.hwagae.market.comment.CommentEntity;
import com.hwagae.market.file.FileEntity;
import com.hwagae.market.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportNum;

    @Column(nullable = true)
    private String reportUname;

    @Column
    private String reportUnick;

    @Column
    private String reportUemail;

    @Column
    private String reportTitle;

    @Column
    private String reportContent;

    @Column
    private String reportSnick;

    @Column
    private String reportSaccount;

    @Column
    private String reportSphone;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp reportDate;

    @Column
    private Integer reportState;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_num" ,insertable = true,updatable = false)
    private UserEntity userEntity;


    @OneToMany(mappedBy = "reportEntity",cascade =CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<FileEntity> fileEntityList=new ArrayList<>();


    @OneToMany(mappedBy = "reportEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList=new ArrayList<>();


    public static ReportEntity toSaveEntity(ReportDTO reportDTO){
        ReportEntity reportEntity=new ReportEntity();
        reportEntity.setReportUname(reportDTO.getReport_Uname());
        reportEntity.setReportUnick(reportDTO.getReport_Unick());
        reportEntity.setReportUemail(reportDTO.getReport_Uemail());
        reportEntity.setReportTitle(reportDTO.getReport_title());
        reportEntity.setReportContent(reportDTO.getReport_content());
        reportEntity.setReportSnick(reportDTO.getReport_Snick());
        reportEntity.setReportSaccount(reportDTO.getReport_Saccount());
        reportEntity.setReportSphone(reportDTO.getReport_Sphone());
        reportEntity.setReportState(0);
        UserEntity userEntity=new UserEntity();
        userEntity.setUserNum(reportDTO.getUser_num());
        reportEntity.setUserEntity(userEntity);
        return reportEntity;
    }

}