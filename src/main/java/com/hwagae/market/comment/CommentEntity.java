
package com.hwagae.market.comment;



import com.hwagae.market.inquiry.InquiryEntity;
import com.hwagae.market.report.ReportEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentNum;

    @Column(nullable = false)
    private String commentWriter;

    @Column
    private String commentContent;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp commentDate;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="qna_num")
    private InquiryEntity inquiryEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_num")
    private ReportEntity reportEntity;
    public static CommentEntity toSaveEntity(CommentDTO commentDTO,InquiryEntity inquiryEntity){
        CommentEntity commentEntity=new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getComment_writer());
        commentEntity.setCommentContent(commentDTO.getComment_content());
        commentEntity.setInquiryEntity(inquiryEntity);//문의번호로 조회한 부모 entity값도 같이 넣어주는ㅁ거래
        return commentEntity;
    }

    public static CommentEntity toSaveEntity(CommentDTO commentDTO,ReportEntity reportEntity){
        CommentEntity commentEntity=new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getComment_writer());
        commentEntity.setCommentContent(commentDTO.getComment_content());
        commentEntity.setReportEntity(reportEntity);
        return commentEntity;
    }

}