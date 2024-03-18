package com.hwagae.market.like;

import com.hwagae.market.post.PostEntity;
import com.hwagae.market.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer like_num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_num")
    private PostEntity postEntity;

    public static LikeEntity toLikeEntity(PostEntity postEntity, UserEntity userEntity){
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setPostEntity(postEntity);
        likeEntity.setUserEntity(userEntity);
        return likeEntity;
    }
}