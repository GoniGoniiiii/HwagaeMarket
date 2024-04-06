package com.hwagae.market.restrictedUser;

import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ResUserService {
    private final ResUserRepository resUserRepository;
    private final UserService userService;


    @Transactional
    public Page<ResUserDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; //한 페이지에 보여줄 글 갯수
        // 페이지 요청
        Page<ResUserEntity> resUserEntities = resUserRepository.findAll(
                PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "resNum"))
        );

        System.out.println("reportEntities.getContent() = " + resUserEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("reportEntities.getTotalElements() = " + resUserEntities.getTotalElements()); // 전체 글갯수
        System.out.println("reportEntities.getNumber() = " + resUserEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("reportEntities.getTotalPages() = " + resUserEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("reportEntities.getSize() = " + resUserEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("reportEntities.hasPrevious() = " + resUserEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("reportEntities.isFirst() = " + resUserEntities.isFirst()); // 첫 페이지 여부
        System.out.println("reportEntities.isLast() = " + resUserEntities.isLast()); // 마지막 페이지 여부

// 목록: id, writer, title, hits, createdTime
        Page<ResUserDTO> resUserDTOS = resUserEntities.map(resUser -> new ResUserDTO(
                resUser.getResNum(),
                resUser.getResUid(),
                resUser.getResUname(),
                resUser.getResUphone(),
                resUser.getResAccount(),
                resUser.getResReason(),
                resUser.getReportCount()
        ));
        return resUserDTOS;
    }

    @Transactional
    public void save(ResUserDTO resUserDTO, UserEntity userEntity) {
        ResUserEntity existingResUser = resUserRepository.findByUserEntity(userEntity);
        if (existingResUser != null) {
            // 이미 해당 유저의 Restricted User가 존재하는 경우
            existingResUser.setReportCount(existingResUser.getReportCount() + 1);
            resUserRepository.save(existingResUser);
        } else {
            // 해당 유저의 Restricted User가 존재하지 않는 경우
            ResUserEntity newResUser = ResUserEntity.toSaveEntity(resUserDTO, userEntity);
            newResUser.setReportCount(1);
            resUserRepository.save(newResUser);
        }

        userService.updateUserRole();

    }

    public int selectResUser(String selectValue, String inputValue) {
        System.out.println("selectValue = " + selectValue + ", inputValue = " + inputValue);
        Integer reportCount = resUserRepository.findReportCountBySelectValueAndInputValue(selectValue, inputValue);
        System.out.println("service의 reportCount값 : " + reportCount);
        return reportCount;
    }
}
