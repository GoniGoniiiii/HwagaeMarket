package com.hwagae.market.notice;

import com.hwagae.market.file.FileEntity;
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
@Table(name = "notice")
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Integer noticeNum;

    @Column(nullable=false)
    private String noticeTitle;

    @Column(nullable = false)
    private String noticeContent;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp noticeRegdate;

    @OneToMany(mappedBy = "noticeEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    //어떤것과 매칭이 되느냐 ? 부모엔티티~ / cascade, onphanRemoval  : cascade속성부여v
    private List<FileEntity> fileEntityList = new ArrayList<>();

    public static NoticeEntity toSaveEntity(NoticeDTO noticeDTO){
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setNoticeTitle(noticeDTO.getNotice_title());
        noticeEntity.setNoticeContent(noticeDTO.getNotice_content());
        return noticeEntity;
    }
    public static NoticeEntity toUpdateEntity(NoticeDTO noticeDTO){
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setNoticeNum(noticeDTO.getNotice_num());
        noticeEntity.setNoticeTitle(noticeDTO.getNotice_title());
        noticeEntity.setNoticeContent(noticeDTO.getNotice_content());
        return noticeEntity;
    }

}