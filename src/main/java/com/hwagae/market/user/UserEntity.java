package com.hwagae.market.user;

import com.hwagae.market.chat.ChatEntity;
import com.hwagae.market.file.FileEntity;
import com.hwagae.market.inquiry.InquiryEntity;
import com.hwagae.market.like.LikeEntity;
import com.hwagae.market.post.PostEntity;
import com.hwagae.market.report.ReportEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private Integer userNum;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false, unique = true)
    private String userNick;

    @Column(nullable = false, unique = true)
    private String userPhone;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userBirth;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userJoindate;

    @Column
    private String userPhoto;

    @Column
    private String userLocation;

    @Column
    private String userLocation2;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostEntity> postEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FileEntity> FileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReportEntity> reportEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InquiryEntity> inquiryEntityList = new ArrayList<>();


    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNum(userDTO.getUser_num());
        userEntity.setUserId(userDTO.getUser_id());
        userEntity.setUserPw(userDTO.getUser_pw());
        userEntity.setUserNick(userDTO.getUser_nick());
        userEntity.setUserPhone(userDTO.getUser_phone());
        userEntity.setUserEmail(userDTO.getUser_email());
        userEntity.setUserBirth(userDTO.getUser_birth());
        userEntity.setUserPhoto(userDTO.getUser_photo());
        userEntity.setUserName(userDTO.getUser_name());
        userEntity.setUserJoindate(userDTO.getUser_joindate());
        userEntity.setUserLocation(userDTO.getUser_location());
        userEntity.setUserLocation2(userDTO.getUser_location2());
        return userEntity;
    }

    public static UserEntity toUserUpdateEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNum(userDTO.getUser_num());
        userEntity.setUserId(userDTO.getUser_id());
        userEntity.setUserPw(userDTO.getUser_pw());
        userEntity.setUserNick(userDTO.getUser_nick());
        userEntity.setUserPhone(userDTO.getUser_phone());
        userEntity.setUserEmail(userDTO.getUser_email());
        userEntity.setUserBirth(userDTO.getUser_birth());
        userEntity.setUserPhoto(userDTO.getUser_photo());
        userEntity.setUserName(userDTO.getUser_name());
        userEntity.setUserJoindate(userDTO.getUser_joindate());
        userEntity.setUserLocation(userDTO.getUser_location());
        userEntity.setUserLocation2(userDTO.getUser_location2());
        return userEntity;
    }
}
