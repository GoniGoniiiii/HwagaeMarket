package com.hwagae.market.inquiry;

import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    @Transactional
    public void save(InquiryDTO inquiryDTO) {
        InquiryEntity inquiryEntity = InquiryEntity.toSaveEntity(inquiryDTO);
        inquiryRepository.save(inquiryEntity);
    }
    @Transactional
    public List<InquiryDTO> findAll() {
        List<InquiryEntity> inquiryEntityList = inquiryRepository.findAll();
        List<InquiryDTO> inquiryDTOList = new ArrayList<>();
        for (InquiryEntity inquiryEntity : inquiryEntityList) {
            inquiryDTOList.add(InquiryDTO.toInquiryDTO(inquiryEntity));
        }
        return inquiryDTOList;
    }
    @Transactional
    public List<InquiryDTO> findAllByUserNum(Integer user_num) {
        UserEntity userEntity = userRepository.findById(user_num).orElse(null);
        if (userEntity != null) {
            List<InquiryEntity> inquiryEntityList = inquiryRepository.findAllByUserEntity(userEntity);
            List<InquiryDTO> inquiryDTOList = new ArrayList<>();
            for (InquiryEntity inquiryEntity : inquiryEntityList) {
                inquiryDTOList.add(InquiryDTO.toInquiryDTO(inquiryEntity));
            }
            return inquiryDTOList;
        } else {
            return null;
        }
    }

    @Transactional
    public InquiryDTO findByNum(Integer qna_num) {
        Optional<InquiryEntity> optionalInquiryEntity = inquiryRepository.findById(qna_num);
        if (optionalInquiryEntity.isPresent()) {
            InquiryEntity inquiryEntity = optionalInquiryEntity.get();
            InquiryDTO inquiryDTO = InquiryDTO.toInquiryDTO(inquiryEntity);
            return inquiryDTO;
        }
        return null;
    }
    @Transactional
    public Page<InquiryDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; //한 페이지에 보여줄 글 갯수
        //한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        //page 위치에 있는 값은 0부터 시작
        Page<InquiryEntity> inquiryEntities =
                inquiryRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "qnaNum")));

        System.out.println("inquiryEntities.getContent() = " + inquiryEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + inquiryEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + inquiryEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + inquiryEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + inquiryEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + inquiryEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + inquiryEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + inquiryEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<InquiryDTO> inquiryDTOS = inquiryEntities.map(inquiry -> new InquiryDTO(inquiry.getQnaNum(),
                inquiry.getQnaTitle(),inquiry.getUserEntity().getUserId(), inquiry.getUserEntity().getUserName(), inquiry.getUserEntity().getUserPhone(),inquiry.getQnaStatus()));
        return inquiryDTOS;
    }

    @Transactional
    // 전체 신고 수를 반환하는 메서드
    public int getTotalQna() {
        return inquiryRepository.getTotalQnaCount();
    }

    @Transactional    // 처리 완료된 신고 수를 반환하는 메서드
    public int getCompletedQna() {
        return inquiryRepository.getCompletedQnaCount();
    }

}