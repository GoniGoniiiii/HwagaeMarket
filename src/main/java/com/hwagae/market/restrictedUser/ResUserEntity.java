package com.hwagae.market.restrictedUser;

import com.hwagae.market.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "restricted_user")
public class ResUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resNum;

    @Column
    private String resReason;

    @Column
    private String resUname;

    @Column
    private String resUid;

    @Column
    private String resUphone;

    @Column
    private String resAccount;

    @Column
    private Integer reportCount;

    @CreationTimestamp
    @Column(insertable = true,updatable = false)
    private LocalDateTime resUserDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_num")
    private UserEntity userEntity;

    public static ResUserEntity toSaveEntity(ResUserDTO resUserDTO,UserEntity userEntity){
        ResUserEntity resUserEntity=new ResUserEntity();
        resUserEntity.setResReason(resUserDTO.getRes_reason());
        resUserEntity.setResAccount(resUserDTO.getRes_account());
        resUserEntity.setResUid(resUserDTO.getRes_uid());
        resUserEntity.setResUname(resUserDTO.getRes_uname());
        resUserEntity.setResAccount(resUserDTO.getRes_account());
        resUserEntity.setResUphone(resUserDTO.getRes_uphone());
        resUserEntity.setUserEntity(userEntity);
        return resUserEntity;
    }

}