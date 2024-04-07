package com.hwagae.market.notification;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor//기본생성자
@AllArgsConstructor//모든 필드를 매개변수로 하는 생성자
public class NotificationDTO {
    public int notification_num;

    public int user_num;

    public String user_id;

    public String notification_message;

    public Timestamp notificationDate;


    public static NotificationDTO toNotificationDTO(NotificationEntity notificationEntity){
        NotificationDTO notificationDTO=new NotificationDTO();
        notificationDTO.setNotification_num(notificationEntity.getNotificationNum());
        notificationDTO.setUser_num(notificationEntity.getUserNum());
        notificationDTO.setUser_id(notificationEntity.getUserId());
        notificationDTO.setNotification_message(notificationEntity.getNotificationMessage());
        return notificationDTO;
    }

}
