package com.hwagae.market.event;

import com.hwagae.market.file.FileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class EventDTO {
    private Integer event_num;
    private String event_title;
    private String event_content;
    private Timestamp event_regdate;
    private String file_num;

    private List<MultipartFile> event_upLoadFile;
    private List<String> originalFileName;
    private List<String> storedFilename;
    private List<String> file_url;

    public static EventDTO toEventDTO(EventEntity eventEntity){
        EventDTO eventDTO=new EventDTO();
        eventDTO.setEvent_num(eventEntity.getEventNum());
        eventDTO.setEvent_title(eventEntity.getEventTitle());
        eventDTO.setEvent_content(eventEntity.getEventContent());
        eventDTO.setEvent_regdate(eventEntity.getEventRegdate());
        List<String> fileList=new ArrayList<>();
        for(FileEntity fileEntity: eventEntity.getFileEntityList()){
            fileList.add(fileEntity.getFileUrl());
        }
        eventDTO.setFile_url(fileList);
        return eventDTO;
    }
}