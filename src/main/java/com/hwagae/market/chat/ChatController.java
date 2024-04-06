package com.hwagae.market.chat;

import com.hwagae.market.user.UserDTO;
import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor

public class ChatController {

    private final ChatRepository chatRepository; // ChatEntity를 저장하고 불러오기 위한 Repository
    private final ChatService chatService;
    private final UserRepository userRepository;

    @GetMapping("/chat/{userNum}")
    public String chatPage(@PathVariable("userNum") Integer userNum, Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");


        UserEntity userEntity = userRepository.findByUserNum(userNum);
        if (userEntity != null) {
            // userEntity 닉네임 추출하여 모델에 추가
            model.addAttribute("toNick", userEntity.getUserNick());
            model.addAttribute("toNum", userEntity.getUserNum());
            System.out.println("유저넘버......"+model);
        }
        System.out.println(" ' " + model + " ' "+"님 과의 채팅방으로 이동");
        if(userDTO != null){
            return "views/user/chat";
        }else {
            return "views/user/login";
        }

    }


    @PostMapping("/chat/send")
    @ResponseBody
    public ChatDTO sendMessage(@ModelAttribute ChatDTO chatDTO) {
        chatService.saveChatMessage(chatDTO);
        System.out.println("전송 성공 " + chatDTO);
        return chatDTO;
    }



    @PostMapping("/chat/record")
    @ResponseBody
    public List<Map<String, Object>> getChatRecord(@RequestParam("toNum") String toNum, @RequestParam("fromNum") String fromNum) {
        List<ChatEntity> chatEntities = chatRepository.findByFromNumAndToNumOrFromNumAndToNumOrderByChatTime(fromNum, toNum, toNum, fromNum);
        System.out.println("chatEntities = " + chatEntities);

        List<Map<String, Object>> chatRecord = new ArrayList<>();
        for (ChatEntity chatEntity : chatEntities) {
            Map<String, Object> chatMap = new HashMap<>();
            chatMap.put("chatNum", chatEntity.getChatNum());
            chatMap.put("fromNum", chatEntity.getFromNum());
            chatMap.put("toNum", chatEntity.getToNum());
            chatMap.put("chatContent", chatEntity.getChatContent());
            chatMap.put("chatTime", chatEntity.getChatTime());
            chatRecord.add(chatMap);
        }

        return chatRecord;
    }


    @GetMapping("/chat/list")
    public String getChatList(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에서 사용자 닉네임 가져오기
        String userNum = String.valueOf(userDTO.getUser_num());
        System.out.println("userNick = " + userNum);

        List<ChatEntity> chatList = chatService.getChatListWithLatestMessage(userNum);
        System.out.println("chatList = " + chatList);

        // 사용자 닉네임을 기반으로 채팅 목록 조회
/*        List<ChatEntity> chatList = chatService.getChatListByID(userNum);*/

        // 각 채팅에 대한 대상 사용자의 닉네임을 가져와서 설정
        for (ChatEntity chat : chatList) {
            String toNum = chat.getToNum(); // 대상 사용자의 고유 번호
            UserEntity toUser = userRepository.findByUserNum(Integer.valueOf(toNum)); // 대상 사용자 엔티티 가져오기
            if (toUser != null) {
                String toNick = toUser.getUserNick(); // 대상 사용자의 닉네임
                chat.setToNick(toNick); // 대상 사용자의 닉네임을 chat 엔티티에 설정
            }
        }

        // 조회된 채팅 목록을 모델에 추가하여 chatList.html로 전달
        model.addAttribute("chatList", chatList);
        System.out.println("채팅리스트트트트트트 " + chatList);
        System.out.println("몯데데데데데ㅔㄷㄹ" + model);

        if(userDTO != null){
            return "views/user/chatList";
        } else {
            return "views/user/login";
        }
    }


}
