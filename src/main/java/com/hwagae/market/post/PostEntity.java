package com.hwagae.market.post;


import com.hwagae.market.category.CategoryEntity;
import com.hwagae.market.file.FileEntity;
import com.hwagae.market.like.LikeEntity;
import com.hwagae.market.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postNum;

    @Column
    private String postTitle;

    @Column
    private String postContent;

    @Column
    private Integer postPrice;

    @Column
    private String postSaleState;

    @Column
    private String postRegdate;

    @Column
    private String postUpdate;

    @Column
    private String postLocation;

    @Column
    private String postLocation2;

    @Column
    private String postWay;

    @Column
    private Integer postHits;

    @Column
    private Integer postLike;

    @Column
    private String postProductState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_num")
    private CategoryEntity categoryEntity;


    @Column
    private Integer fileAttached = 0;

    public Integer getFileAttached() {
        return fileAttached != null ? fileAttached : 0;
    }

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    //어떤것과 매칭이 되느냐 ? 부모엔티티~ / cascade, onphanRemoval  : cascade속성부여v
    private List<FileEntity> fileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList = new ArrayList<>();




    public static PostEntity toSaveEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserNum(postDTO.getUser_num()); // 유저 번호 설정
        postEntity.setUserEntity(userEntity); // 유저 엔티티 설정

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryNum(postDTO.getCategory_num()); // 카테고리 번호 설정
        postEntity.setCategoryEntity(categoryEntity); // 카테고리 엔티티 설정

        postEntity.setPostNum(postDTO.getPost_num());
        postEntity.setPostTitle(postDTO.getPost_title());
        postEntity.setPostContent(postDTO.getPost_content());
        postEntity.setPostPrice(postDTO.getPost_price());
        postEntity.setPostSaleState(postDTO.getPost_saleState());
        postEntity.setPostRegdate(postDTO.getPost_regdate());
        postEntity.setPostUpdate(postDTO.getPost_update());
        postEntity.setPostLocation(postDTO.getPost_location());
        postEntity.setPostLocation2(postDTO.getPost_location2());
        postEntity.setPostWay(postDTO.getPost_way());
        postEntity.setPostHits(postDTO.getPost_hits());
        postEntity.setPostLike(postDTO.getPost_like());
        postEntity.setPostProductState(postDTO.getPost_productState());
        postEntity.setUserEntity(postEntity.getUserEntity());
        postEntity.setCategoryEntity(postEntity.getCategoryEntity());
        postEntity.setFileAttached(0); //파일 없음
        return postEntity;
    }

    public static PostEntity toSaveFileEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserNum(postDTO.getUser_num()); // 유저 번호 설정
        postEntity.setUserEntity(userEntity); // 유저 엔티티 설정

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryNum(postDTO.getCategory_num()); // 카테고리 번호 설정
        postEntity.setCategoryEntity(categoryEntity); // 카테고리 엔티티 설정

        postEntity.setPostNum(postDTO.getPost_num());
        postEntity.setPostTitle(postDTO.getPost_title());
        postEntity.setPostContent(postDTO.getPost_content());
        postEntity.setPostPrice(postDTO.getPost_price());
        postEntity.setPostSaleState(postDTO.getPost_saleState());
        postEntity.setPostRegdate(postDTO.getPost_regdate());
        postEntity.setPostUpdate(postDTO.getPost_update());
        postEntity.setPostLocation(postDTO.getPost_location());
        postEntity.setPostLocation2(postDTO.getPost_location2());
        postEntity.setPostWay(postDTO.getPost_way());
        postEntity.setPostHits(postDTO.getPost_hits());
        postEntity.setPostLike(postDTO.getPost_like());
        postEntity.setPostProductState(postDTO.getPost_productState());

        postEntity.setFileAttached(1); //파일 없음
        return postEntity;
    }


}