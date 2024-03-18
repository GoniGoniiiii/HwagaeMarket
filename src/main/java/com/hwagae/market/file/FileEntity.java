package com.hwagae.market.file;

import com.hwagae.market.event.EventEntity;
import com.hwagae.market.notice.NoticeEntity;
import com.hwagae.market.post.PostEntity;
import com.hwagae.market.report.ReportEntity;
import com.hwagae.market.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileNum;

    @Column
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_num")
    private NoticeEntity noticeEntity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="event_num")
    private EventEntity eventEntity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_num")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="report_num")
    private ReportEntity reportEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private UserEntity userEntity;


    public static FileEntity toFileEntity(NoticeEntity noticeEntity, String fileUrl){
        FileEntity fileEntity=new FileEntity();
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setNoticeEntity(noticeEntity);
        return fileEntity;
    }

    // Update 로직 추가
    public void updateFileUrl(String newFileUrl){
        this.fileUrl = newFileUrl;
    }

    public static FileEntity toFileEntity(EventEntity eventEntity,String fileUrl){
        FileEntity fileEntity=new FileEntity();
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setEventEntity(eventEntity);
        return fileEntity;
    }
    public static FileEntity toFileEntity(PostEntity postEntity,String fileUrl){
        FileEntity fileEntity=new FileEntity();
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setPostEntity(postEntity);
        return fileEntity;
    }

    public static FileEntity toFileEntity(ReportEntity reportEntity,String fileUrl){
        FileEntity fileEntity=new FileEntity();
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setReportEntity(reportEntity);
        return fileEntity;
    }

}