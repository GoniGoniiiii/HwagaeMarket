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



    @Transient // 데이터베이스에 저장하지 않을 속성을 지정할 때 사용합니다.
    private String toNick;

    // 대상 사용자의 닉네임 설정 메서드
    public void setToNick(String toNick) {
        this.toNick = toNick;
    }
}
