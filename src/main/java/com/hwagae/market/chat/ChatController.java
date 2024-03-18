package com.hwagae.market.chat;

import com.hwagae.market.user.UserDTO;
import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ChatController {

    private final ChatRepository chatRepository; // ChatEntity를 저장하고 불러오기 위한 Repository
    private final ChatService chatService;
    private final UserRepository userRepository;
    @Autowired
    public ChatController(ChatRepository chatRepository, ChatService chatService, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

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


/*    @GetMapping("/chat/chat/{userNum}")
    public String chatChatPage(@PathVariable("userNum") Integer userNum) {

*//*        System.out.println("채팅 주고받은 닉네임 = " + userNum);

        Optional<UserEntity> userEntityOptional = userRepository.findByUserNick(String.valueOf(userNum));
        if (userEntityOptional.isPresent()) {
            // userNum이 존재하는 경우 해당 값을 userNum으로 리디렉션
            System.out.println("닉네임을 user_num으로 변환함 : " + userEntityOptional.get().getUserNick());
            return "redirect:/chat/" + userNum;
        } else {
            // 사용자를 찾을 수 없는 경우 적절한 처리를 수행
            return "views/user/login"; // 예시로 에러 페이지를 리턴하도록 수정하세요
        }*//*
            return "redirect:/chat/" + userNum;
    }*/


    @PostMapping("/chat/send")
    @ResponseBody
    public ChatDTO sendMessage(@ModelAttribute ChatDTO chatDTO) {
        chatService.saveChatMessage(chatDTO);
        System.out.println("전송 성공 " + chatDTO);
        return chatDTO;
    }




/*    @GetMapping("/chat/list")
    @ResponseBody
    public List<ChatEntity> getChatList(@RequestParam("toID") String toID, @RequestParam("fromID") String fromID) {
        System.out.println("받는사람 = " + toID);
        // 세션에 저장된 사용자의 user_num을 가져옴
        System.out.println("보낸사람 = " + fromID);
        // 채팅 데이터베이스에서 해당하는 채팅 리스트를 가져옴
        List<ChatEntity> chatEntities = chatRepository.findByFromIDAndToIDOrFromIDAndToIDOrderByChatTime(fromID, toID, toID, fromID);
        System.out.println("채팅리스트 = " + chatEntities);

        return chatEntities;
    }*/


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



/*    @PostMapping("/chat/list")
    public String getChatList(@RequestParam("userNum") String userNum) {
        chatRepository.findLatestChatsByUserId(userNum);
        System.out.println("흠;;;; = " + userNum);
        *//*return chatRepository.findLatestChatsByUserId(userNum);*//*
        return "/views/user/chat";
    }*/



// ChatController.java

    @GetMapping("/chat/list")
    public String getChatList(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        // 세션에서 사용자 닉네임 가져오기
        String userNum = String.valueOf(userDTO.getUser_num());
        System.out.println("userNick = " + userNum);

        // 사용자 닉네임을 기반으로 채팅 목록 조회
        List<ChatEntity> chatList = chatService.getChatListByID(userNum);

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
