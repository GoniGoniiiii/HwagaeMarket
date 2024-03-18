
package com.hwagae.market.notice;

import com.hwagae.market.file.FileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
@ToString
public class NoticeDTO {
    private Integer notice_num;
    private String notice_title;
    private String notice_content;
    private Timestamp notice_regdate;
//    private Integer file_num;

    private List<MultipartFile> notice_upLoadFile; //컨트롤러로 넘어올때 담는 용도
    private List<String> originalFileName; //진짜 파일이름
    private List<String> file_url; //서버에 전송할 파일 이름


    public static NoticeDTO toNoticeDTO(NoticeEntity noticeEntity){
        NoticeDTO noticeDTO=new NoticeDTO();
        noticeDTO.setNotice_num(noticeEntity.getNoticeNum());
        noticeDTO.setNotice_title(noticeEntity.getNoticeTitle());
        noticeDTO.setNotice_content(noticeEntity.getNoticeContent());
        noticeDTO.setNotice_regdate(noticeEntity.getNoticeRegdate());
        List<String> fileList=new ArrayList<>();
        for(FileEntity fileEntity: noticeEntity.getFileEntityList()){
            fileList.add(fileEntity.getFileUrl());
        }
        noticeDTO.setFile_url(fileList);
        return noticeDTO;
    }

}
