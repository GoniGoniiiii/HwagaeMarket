package com.hwagae.market.inquiry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든필드를 매개변수로 하는 생성자
public class InquiryDTO {
    private Integer qna_num;
    private String qna_title;
    private String qna_content;
    private Timestamp qna_Qdate;
    private String qna_answer;
    private Timestamp qna_Adate;
    private Integer user_num;
    private String user_name;
    private String user_id;
    private String user_phone;
    private Integer qna_status;

    public InquiryDTO(Integer qnaNum, String qnaTitle, String userId, String userName, String userPhone, Integer qnaStatus) {
        this.qna_num=qnaNum;
        this.qna_title=qnaTitle;
        this.user_id=userId;
        this.user_name=userName;
        this.user_phone=userPhone;
        this.qna_status=qnaStatus;
    }

    public static InquiryDTO toInquiryDTO(InquiryEntity inquiryEntity){
        InquiryDTO inquiryDTO=new InquiryDTO();
        inquiryDTO.setQna_num(inquiryEntity.getQnaNum());
        inquiryDTO.setQna_title(inquiryEntity.getQnaTitle());
        inquiryDTO.setQna_content(inquiryEntity.getQnaContent());
        inquiryDTO.setQna_Qdate(inquiryEntity.getQnaQdate());
        inquiryDTO.setQna_status(inquiryEntity.getQnaStatus());
        inquiryDTO.setUser_num(inquiryEntity.getUserEntity().getUserNum());
        inquiryDTO.setUser_name(inquiryEntity.getUserEntity().getUserName());
        inquiryDTO.setUser_id(inquiryEntity.getUserEntity().getUserId());
        inquiryDTO.setUser_phone(inquiryEntity.getUserEntity().getUserPhone());
        return inquiryDTO;
    }
}