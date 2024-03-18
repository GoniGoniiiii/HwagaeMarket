package com.hwagae.market.inquiry;

import com.hwagae.market.comment.CommentEntity;
import com.hwagae.market.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="qna")
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Integer qnaNum;

    @Column(nullable = true)
    private String qnaTitle;

    @Column(nullable = true)
    private String qnaContent;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp qnaQdate;

    @Column
    private String qnaAnswer;

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp qnaAdate;

    @Column
    private Integer qnaStatus; // 1 or 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_num",insertable = true, updatable = false)
    private UserEntity userEntity;


    @OneToMany(mappedBy = "inquiryEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList=new ArrayList<>();

    public static InquiryEntity toSaveEntity(InquiryDTO inquiryDTO){
        InquiryEntity inquiryEntity=new InquiryEntity();
        inquiryEntity.setQnaTitle(inquiryDTO.getQna_title());
        inquiryEntity.setQnaContent(inquiryDTO.getQna_content());
        inquiryEntity.setQnaStatus(0);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNum(inquiryDTO.getUser_num());
        inquiryEntity.setUserEntity(userEntity);

        return inquiryEntity;
    }


}