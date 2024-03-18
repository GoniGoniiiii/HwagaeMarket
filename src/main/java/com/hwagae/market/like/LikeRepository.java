package com.hwagae.market.like;

import com.hwagae.market.post.PostEntity;
import com.hwagae.market.post.PostRepository;
import com.hwagae.market.user.UserDTO;
import com.hwagae.market.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    LikeEntity findByPostEntityAndUserEntity(PostEntity postEntity, UserEntity userEntity);

    @Query("SELECT l.postEntity.postNum FROM LikeEntity l where l.userEntity.userNum = :userNum")
    List<Integer> findPostNumsByUserNum(@Param("userNum") Integer userNum);

    List<LikeEntity> findByPostEntityPostNum(Integer postNum);

}