package com.hwagae.market.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {


    List<ChatEntity> findByFromNumAndToNumOrFromNumAndToNumOrderByChatTime(String fromNum1, String toNum1, String FromNum2, String toNum2);

/*
    List<ChatEntity> findLatestChatsByFromID(String userNick);
*/

/*    @Query("SELECT c FROM ChatEntity c WHERE (c.fromID = :userNick OR c.toID = :userNick) AND c.chatID = (SELECT MAX(cc.chatID) FROM ChatEntity cc WHERE cc.fromID = c.fromID AND cc.toID = c.toID)")
    List<ChatEntity> findLatestChatsByFromID(@Param("userNick") String userNick);*/

/*    @Query("SELECT c FROM ChatEntity c WHERE c.toID != :userNick AND c.chatID = (SELECT MAX(cc.chatID) FROM ChatEntity cc WHERE cc.fromID = c.fromID AND cc.toID = c.toID)")
    List<ChatEntity> findLatestChatsNotToUser(@Param("userNick") String userNick);*/

    @Query("SELECT c FROM ChatEntity c WHERE c.fromNum = :userNum AND c.toNum IN (SELECT DISTINCT c1.toNum FROM ChatEntity c1) AND c.chatNum = (SELECT MAX(cc.chatNum) FROM ChatEntity cc WHERE cc.fromNum = c.fromNum AND cc.toNum = c.toNum) order by c.chatTime desc")
    List<ChatEntity> findLatestChatsByFromNum(@Param("userNum") String userNum);

}