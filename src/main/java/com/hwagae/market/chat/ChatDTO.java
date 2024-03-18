package com.hwagae.market.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDTO {

    int chatNum;
    String fromNum;
    String toNum;
    String chatContent;
    String chatTime;


}
