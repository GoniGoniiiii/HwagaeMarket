package com.hwagae.market.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void saveChatMessage(ChatDTO chatDTO) {

        // ChatDTO에서 ChatEntity로 변환하여 저장합니다.
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setFromNum(chatDTO.getFromNum());
        chatEntity.setToNum(chatDTO.getToNum());
        chatEntity.setChatContent(chatDTO.getChatContent());
        chatEntity.setChatTime(chatDTO.getChatTime());

        // 저장
        chatRepository.save(chatEntity);
    }


    public List<ChatEntity> getChatListByID(String userNum) {
        return chatRepository.findLatestChatsByFromNum(userNum);
    }



    public List<ChatEntity> getChatListWithLatestMessage(String userNum) {
        List<ChatEntity> chatList = chatRepository.findLatestChatsByFromNum(userNum);

        // 최신 메시지만 남기기
        List<ChatEntity> filteredList = new ArrayList<>();
        for (ChatEntity chat : chatList) {
            boolean isNewest = true;
            for (ChatEntity otherChat : chatList) {
                if (chat.getFromNum().equals(otherChat.getToNum()) && chat.getToNum().equals(otherChat.getFromNum())) {
                    if (chat.getChatNum() < otherChat.getChatNum()) {
                        isNewest = false;
                        break;
                    }
                }
            }
            if (isNewest) {
                // 세션의 user_num과 toNum이 같으면 toNum을 fromNum으로, 그렇지 않으면 그대로 사용
                String fromNum = chat.getToNum().equals(userNum) ? chat.getToNum() : chat.getFromNum();
                String toNum = chat.getToNum().equals(userNum) ? chat.getFromNum() : chat.getToNum();
                chat.setFromNum(fromNum);
                chat.setToNum(toNum);
                filteredList.add(chat);
            }
        }
        Collections.reverse(filteredList);

        return filteredList;
    }


}
