package com.hwagae.market.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository <PostEntity, Integer>{

    // 검색결과 + 페이징처리
    Page<PostEntity> findByPostTitleContaining(String keyword, Pageable pageable);

    Page<PostEntity> findByPostTitleContainingOrPostContentContaining(String keywordT, String keywordC, Pageable pageable);

    // 판매 내역 + 페이징
    Page<PostEntity> findByUserEntityUserNum(Integer userNum, Pageable pageable);

    // 카테고리 결과 + 페이징
    Page<PostEntity> findByCategoryEntityCategoryNum(Integer categoryNum, Pageable pageable);


    PostEntity findFirstByOrderByPostNumDesc();

}
