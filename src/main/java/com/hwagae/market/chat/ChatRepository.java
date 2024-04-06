package com.hwagae.market.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

/*    @Query("SELECT c FROM ChatEntity c WHERE c.fromNum = :userNum AND c.toNum IN (SELECT DISTINCT c1.toNum FROM ChatEntity c1) AND c.chatNum = (SELECT MAX(cc.chatNum) FROM ChatEntity cc WHERE cc.fromNum = c.fromNum AND cc.toNum = c.toNum) order by c.chatTime desc")
    List<ChatEntity> findLatestChatsByFromNum(@Param("userNum") String userNum);*/

/*    @Query("select * from chat where chat IN (select max(chatID) from chat where toNum = userNum or fromNum = userNum group by fromNum, toNum)")*/
    @Query("SELECT c FROM ChatEntity c WHERE c.chatNum IN (SELECT MAX(c2.chatNum) FROM ChatEntity c2 WHERE c2.toNum = :userNum OR c2.fromNum = :userNum GROUP BY c2.fromNum, c2.toNum)")
    List<ChatEntity> findLatestChatsByFromNum(@Param("userNum") String userNum);




}