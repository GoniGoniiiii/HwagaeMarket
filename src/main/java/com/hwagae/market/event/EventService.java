package com.hwagae.market.event;

import com.hwagae.market.file.FileEntity;
import com.hwagae.market.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final FileRepository fileRepository;

    public void save(EventDTO eventDTO) throws IOException {
        if (eventDTO.getEvent_upLoadFile().isEmpty()) {
            EventEntity eventEntity = EventEntity.toSaveEntity(eventDTO);
            eventRepository.save(eventEntity);
        } else {
            EventEntity eventEntity = EventEntity.toSaveEntity(eventDTO);
            Integer saveNum = eventRepository.save(eventEntity).getEventNum();
            EventEntity event = eventRepository.findById(saveNum).get();

            for (MultipartFile eventFile : eventDTO.getEvent_upLoadFile()) {
                String originalFileName = eventFile.getOriginalFilename();

                if (originalFileName != null && !originalFileName.isEmpty()) {
                    String file_url = System.currentTimeMillis() + originalFileName;
                    String savePath = "C:/image/" + file_url;
                    eventFile.transferTo(new File(savePath));
                    FileEntity fileEntity = FileEntity.toFileEntity(event, file_url);
                    fileRepository.save(fileEntity);
                }else{
                    System.out.println("파일이 입력되지않았습니다!");
                }
            }
        }
    }

    public List<EventDTO> findAll () {
        List<EventEntity> eventEntityList = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();
        for (EventEntity eventEntity : eventEntityList) {
            eventDTOList.add(EventDTO.toEventDTO(eventEntity));
        }
        return eventDTOList;
    }

    public EventDTO findByNum (Integer event_num){
        Optional<EventEntity> optionalEventEntity = eventRepository.findById(event_num);
        if (optionalEventEntity.isPresent()) {
            EventEntity eventEntity = optionalEventEntity.get();
            EventDTO eventDTO = EventDTO.toEventDTO(eventEntity);
            return eventDTO;
        }
        return null;
    }

    public EventDTO update (EventDTO eventDTO) throws Exception {
        if (eventDTO.getEvent_num() != null) {
            EventEntity exsitingEvent = eventRepository.findById(eventDTO.getEvent_num()).orElse(null);

            if (exsitingEvent != null) {
                exsitingEvent.setEventTitle(eventDTO.getEvent_title());
                exsitingEvent.setEventContent(eventDTO.getEvent_content());

                exsitingEvent.getFileEntityList().clear();

                if (!eventDTO.getEvent_upLoadFile().isEmpty()) {
                    for (MultipartFile eventFile : eventDTO.getEvent_upLoadFile()) {
                        String originalFileName = eventFile.getOriginalFilename();
                        if (originalFileName != null && !originalFileName.isEmpty()) {
                            String file_url = System.currentTimeMillis() + "_" + originalFileName;
                            String savePath = "C:/image/" + file_url;
                            eventFile.transferTo(new File(savePath));

                            FileEntity fileEntity = FileEntity.toFileEntity(exsitingEvent, file_url);
                            fileEntity.updateFileUrl(file_url);
                            fileRepository.save(fileEntity);
                        } else {
                            System.out.println("파일이 업로드되지않았습니다!");
                        }
                    }
                }
                eventRepository.save(exsitingEvent);
                return findByNum(eventDTO.getEvent_num());
            } else {
                throw new Exception("해당번호로 된 이벤트가 존재하지 않습니다." + eventDTO.getEvent_num());
            }
        } else {
            throw new IllegalArgumentException("이벤트 번호가 필요합니다.");
        }
    }

    public void delete (Integer event_num){
        eventRepository.deleteById(event_num);
    }
}