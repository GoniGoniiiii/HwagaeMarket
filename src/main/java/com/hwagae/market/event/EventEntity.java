package com.hwagae.market.event;

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
@Table(name="event")
public class EventEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventNum;

    @Column(nullable = false)
    private String eventTitle;

    @Column(nullable = true)
    private String eventContent;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp eventRegdate;

    @OneToMany(mappedBy = "eventEntity",cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<FileEntity> fileEntityList=new ArrayList<>();

    public static EventEntity toSaveEntity(EventDTO eventDTO){
        EventEntity eventEntity=new EventEntity();
        eventEntity.setEventTitle(eventDTO.getEvent_title());
        eventEntity.setEventContent(eventDTO.getEvent_content());
        return eventEntity;
    }

}