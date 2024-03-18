package com.hwagae.market.like;

import com.hwagae.market.post.PostDTO;
import com.hwagae.market.post.PostEntity;
import com.hwagae.market.post.PostRepository;
import com.hwagae.market.post.PostService;
import com.hwagae.market.user.UserDTO;
import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostService postService;

    public LikeDTO findByPostNum(Integer postNum, UserDTO userDTO){
        PostEntity postEntity = postRepository.findById(postNum).get();

        UserEntity userEntity = UserEntity.toUserEntity(userDTO);

        LikeEntity likeEntity = likeRepository.findByPostEntityAndUserEntity(postEntity, userEntity);


        LikeDTO likeDTO = new LikeDTO();
        if(likeEntity!=null){
            likeDTO = LikeDTO.toLikeDTO(likeEntity);
        }


        return likeDTO;
    }

    @Transactional
    public Boolean like(LikeDTO likeDTO){

        System.out.println("post_num : "+likeDTO.getPostNum());
        System.out.println("user_num : "+likeDTO.getUserNum());

        Optional<PostEntity> optionalPostEntity = postRepository.findById(likeDTO.getPostNum());
        Optional<UserEntity> optionalUserEntity = userRepository.findById(likeDTO.getUserNum());

        if(optionalPostEntity.isPresent()&&optionalUserEntity.isPresent()){
            PostEntity postEntity = optionalPostEntity.get();
            UserEntity userEntity = optionalUserEntity.get();

            LikeEntity likes = LikeEntity.toLikeEntity(postEntity, userEntity);
            LikeEntity likeEntity = likeRepository.findByPostEntityAndUserEntity(postEntity, userEntity);

            if(likeEntity!=null){
                likeRepository.deleteById(likeEntity.getLike_num());
            }else{
                likeRepository.save(likes);
            }

            return true;
        }
        else{
            return false;
        }
    }

    // 로그인한 user가 좋아요를 누른 상태면 채워진 하트를 보여주기 위해
    @Transactional
    public List<PostDTO> findLikedPostsByUserNum(Integer userNum){
        List<Integer> postNums = likeRepository.findPostNumsByUserNum(userNum);

        return postService.findPostsByPostNums(postNums);
    }

    // 게시글의 총 좋아요 갯수
    @Transactional
    public int countLikes(Integer postNum){
        List<LikeEntity> likes = likeRepository.findByPostEntityPostNum(postNum);
        return likes.size();
    }
}