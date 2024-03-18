package com.hwagae.market.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserDTO userDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메소드 호출

        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
        // repository의 save 메소드 호출 (조건 = entity객체를 넘겨줘야 함)
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<UserEntity> id = userRepository.findByUserId(userDTO.getUser_id());
        if (id.isPresent()){
            UserEntity userEntity = id.get();
            if(userEntity.getUserPw().equals(userDTO.getUser_pw())){
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }


    public String findID(UserDTO userDTO) {
        Optional<UserEntity> findID = userRepository.findByUserName(userDTO.getUser_name());
        if(findID.isPresent()){
            UserEntity userEntity = findID.get();
            if (userEntity.getUserName().equals(userDTO.getUser_name()) &&
                    userEntity.getUserEmail().equals(userDTO.getUser_email()) &&
                    userEntity.getUserPhone().equals(userDTO.getUser_phone())) {
                return userEntity.getUserId();
            } else {
                String message = "일치하는 정보가 없습니다. ";
                if (!userEntity.getUserEmail().equals(userDTO.getUser_email()) || !userEntity.getUserPhone().equals(userDTO.getUser_phone())) {
                    message += "이메일주소 혹은 전화번호를 다시 확인하세요. ";
                }
                if (!userEntity.getUserName().equals(userDTO.getUser_name())) {
                    message += "이름 혹은 이메일주소를 다시 확인하세요. ";
                }
                return message;
            }
        } else {
            return "일치하는 정보가 없습니다.";
        }
    }



    public UserDTO findPW(UserDTO userDTO) {
        Optional<UserEntity> findPW = userRepository.findByUserName(userDTO.getUser_name());
        if(findPW.isPresent()){
            UserEntity userEntity = findPW.get();
            if (userEntity.getUserId().equals(userDTO.getUser_id()) &&
                    userEntity.getUserName().equals(userDTO.getUser_name()) &&
                    userEntity.getUserEmail().equals(userDTO.getUser_email()) &&
                    userEntity.getUserPhone().equals(userDTO.getUser_phone())){
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            }else{
                return null;
            }
        }else {
            return null;
        }
    }


    public UserDTO updateForm(String myInfo) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(myInfo);
        if(optionalUserEntity.isPresent()){
            return UserDTO.toUserDTO(optionalUserEntity.get());
        }else {
            return null;
        }
    }

    public void updatePw(UserDTO userDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userDTO.getUser_num());
        if(optionalUserEntity.isPresent()){
            userDTO.setUser_id(optionalUserEntity.get().getUserId());
            userDTO.setUser_nick(optionalUserEntity.get().getUserNick());
            userDTO.setUser_phone(optionalUserEntity.get().getUserPhone());
            userDTO.setUser_email(optionalUserEntity.get().getUserEmail());
            userDTO.setUser_birth(optionalUserEntity.get().getUserBirth());
            userDTO.setUser_name(optionalUserEntity.get().getUserName());
            userDTO.setUser_joindate(optionalUserEntity.get().getUserJoindate());
            userDTO.setUser_photo(optionalUserEntity.get().getUserPhoto());
            userDTO.setUser_location(optionalUserEntity.get().getUserLocation());
            userDTO.setUser_location2(optionalUserEntity.get().getUserLocation2());
        }else {
            throw new NullPointerException("에러");
        }
        userRepository.save(UserEntity.toUserUpdateEntity(userDTO));
    }

    public void updateNick(UserDTO userDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userDTO.getUser_num());
        if(optionalUserEntity.isPresent()){
            userDTO.setUser_id(optionalUserEntity.get().getUserId());
            userDTO.setUser_pw(optionalUserEntity.get().getUserPw());
            userDTO.setUser_phone(optionalUserEntity.get().getUserPhone());
            userDTO.setUser_email(optionalUserEntity.get().getUserEmail());
            userDTO.setUser_birth(optionalUserEntity.get().getUserBirth());
            userDTO.setUser_name(optionalUserEntity.get().getUserName());
            userDTO.setUser_joindate(optionalUserEntity.get().getUserJoindate());
            userDTO.setUser_photo(optionalUserEntity.get().getUserPhoto());
            userDTO.setUser_location(optionalUserEntity.get().getUserLocation());
            userDTO.setUser_location2(optionalUserEntity.get().getUserLocation2());
        }else {
            throw new NullPointerException("에러");
        }
        userRepository.save(UserEntity.toUserUpdateEntity(userDTO));
    }


    public void updatePhoto(UserDTO userDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userDTO.getUser_num());
        if(optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setUserPhoto(userDTO.getUser_photo()); // 사용자 사진 업데이트
            userRepository.save(userEntity); // 변경된 정보를 저장
        } else {
            throw new NullPointerException("에러");
        }
    }

    public void updateLocation(UserDTO userDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userDTO.getUser_num());
        if(optionalUserEntity.isPresent()){
            userDTO.setUser_id(optionalUserEntity.get().getUserId());
            userDTO.setUser_pw(optionalUserEntity.get().getUserPw());
            userDTO.setUser_nick(optionalUserEntity.get().getUserNick());
            userDTO.setUser_phone(optionalUserEntity.get().getUserPhone());
            userDTO.setUser_email(optionalUserEntity.get().getUserEmail());
            userDTO.setUser_birth(optionalUserEntity.get().getUserBirth());
            userDTO.setUser_name(optionalUserEntity.get().getUserName());
            userDTO.setUser_joindate(optionalUserEntity.get().getUserJoindate());
            userDTO.setUser_photo(optionalUserEntity.get().getUserPhoto());
        }else {
            throw new NullPointerException("에러");
        }
        userRepository.save(UserEntity.toUserUpdateEntity(userDTO));
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }


    public String idCheck(String userId) {
        Optional<UserEntity> byUserId = userRepository.findByUserId(userId);
        if(byUserId.isPresent()){
            //조회결과가 있으면 사용 불가
            return null;
        }else {
            //조회결과가 없으면 사용 가능
            return "ok";
        }
    }

    public String nickCheck(String userNick) {
        Optional<UserEntity> byUserNick = userRepository.findByUserNick(userNick);
        if(byUserNick.isPresent()){
            //조회결과가 있으면 사용 불가
            return null;
        }else {
            //조회결과가 없으면 사용 가능
            return "ok";
        }
    }

    public String emailCheck(String userEmail) {
        Optional<UserEntity> byUserEmail = userRepository.findByUserEmail(userEmail);
        if(byUserEmail.isPresent()){
            return null;
        }else {
            return "ok";
        }
    }


    ///////////////////포스트랑 유저 엮은 부분////////////////////////////////
    public UserEntity getUserByUserNum(Integer userNum) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userNum);
        return optionalUserEntity.orElse(null);
    }

    public UserDTO getUserByUserNick(String postNick) {
        Optional<UserEntity> userDTO = userRepository.findByUserNick(postNick);
        if(userDTO.isPresent()){
            UserEntity userEntity = userDTO.get();
            return UserDTO.toUserDTO(userEntity);
        }else{
            return null;
        }
    }


    public List<UserDTO> findUsersByReportConditions(String reportSphone, String reportSnick) {
        List<UserEntity> userEntities = userRepository.findUsersByReportConditions(reportSphone, reportSnick);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserDTO userDTO = UserDTO.toUserDTO(userEntity); // 변환 메소드 호출
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public UserEntity findByNum(Integer user_num) {
        Optional<UserEntity> optionalUser = userRepository.findById(user_num);
        return optionalUser.orElse(null); // optional에서 UserEntity 추출
    }

}