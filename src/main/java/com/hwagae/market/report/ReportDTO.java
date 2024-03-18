package com.hwagae.market.report;

import com.hwagae.market.file.FileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportDTO {
    private Integer report_num;
    private String report_Uname;
    private String report_Unick;
    private String report_Uemail;
    private String report_title;
    private String report_content;
    private String report_Snick;
    private String report_Saccount;
    private String report_Sphone;
    private Timestamp report_date;
    private Integer report_state;
    private Integer user_num;

    private List<MultipartFile> report_upLoadFile;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private List<String> file_url;

    public ReportDTO(Integer reportNum, String reportTitle, Integer reportState, Timestamp reportDate) {
        this.report_num = reportNum;
        this.report_title = reportTitle;
        this.report_state = reportState;
        this.report_date = reportDate;
    }

    public ReportDTO(Integer reportNum, String reportTitle, String reportUname, Timestamp reportDate,Integer reportState ) {
        this.report_num=reportNum;
        this.report_title=reportTitle;
        this.report_Uname=reportUname;
        this.report_date=reportDate;
        this.report_state=reportState;
    }


    public static ReportDTO toReportDTO(ReportEntity reportEntity) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReport_num(reportEntity.getReportNum());
        reportDTO.setReport_title(reportEntity.getReportTitle());
        reportDTO.setReport_content(reportEntity.getReportContent());
        reportDTO.setReport_date(reportEntity.getReportDate());
        reportDTO.setReport_Snick(reportEntity.getReportSnick());
        reportDTO.setReport_Saccount(reportEntity.getReportSaccount());
        reportDTO.setReport_Sphone(reportEntity.getReportSphone());
        reportDTO.setReport_state(reportEntity.getReportState());
        reportDTO.setUser_num(reportEntity.getUserEntity().getUserNum());
        reportDTO.setReport_Uname(reportEntity.getUserEntity().getUserName());
        reportDTO.setReport_Unick(reportEntity.getUserEntity().getUserNick());
        reportDTO.setReport_Uemail(reportEntity.getUserEntity().getUserEmail());
        reportDTO.setReport_state(reportEntity.getReportState());
        List<String> fileList = new ArrayList<>();
        for (FileEntity fileEntity : reportEntity.getFileEntityList()) {
            fileList.add(fileEntity.getFileUrl());
        }
        reportDTO.setFile_url(fileList);
        System.out.println("ReportDTO : "+ reportDTO.toString());
        return reportDTO;
    }

}