package com.hwagae.market.chat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatNum;

    @Column
    private String fromNum;

    @Column
    private String toNum;

    @Column
    private String chatContent;

    @Column
    private String chatTime;


}
