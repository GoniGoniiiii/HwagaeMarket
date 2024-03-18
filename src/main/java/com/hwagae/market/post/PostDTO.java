package com.hwagae.market.post;

import com.hwagae.market.file.FileEntity;
import com.hwagae.market.user.UserDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Integer post_num;
    private String post_title;
    private String post_content;
    private Integer post_price;
    private String post_saleState;
    private String post_regdate;
    private String post_update;
    private String post_location;
    private String post_location2;
    private String post_way;
    private Integer post_hits;
    private Integer post_like;
    private String post_productState;
    private Integer user_num;
    private Integer category_num;
    private String categoryName;
    private Integer file_attached;

    /* 사진 첨부 */
    private List<MultipartFile> post_upLoadFile; //컨트롤러로 넘어올때 담는 용도
    private List<String> originalFileName; //진짜 파일이름
    private List<String> file_url; //서버에 전송할 파일 이름

    /* 유저 가져오기 */
    private String user_id;
    private String user_pw;
    private String user_nick;
    private String user_phone;
    private String user_email;
    private String user_birth;
    private String user_name;
    private String user_role;
    private String user_joindate;
    private String user_photo;
    private MultipartFile upLoadFile;
    private String user_location;
    private String user_location2;



    public static PostDTO toPostDTO(PostEntity postEntity){
        PostDTO postDTO = new PostDTO();
        postDTO.setPost_num(postEntity.getPostNum());
        postDTO.setPost_title(postEntity.getPostTitle());
        postDTO.setPost_content(postEntity.getPostContent());
        postDTO.setPost_price(postEntity.getPostPrice());
        postDTO.setPost_saleState(postEntity.getPostSaleState());
        postDTO.setPost_regdate(postEntity.getPostRegdate());
        postDTO.setPost_update(postEntity.getPostUpdate());
        postDTO.setPost_location(postEntity.getPostLocation());
        postDTO.setPost_location2(postEntity.getPostLocation2());
        postDTO.setPost_way(postEntity.getPostWay());
        postDTO.setPost_hits(postEntity.getPostHits());
        postDTO.setPost_like(postEntity.getPostLike());
        postDTO.setPost_productState(postEntity.getPostProductState());
        postDTO.setUser_num(postEntity.getUserEntity().getUserNum());
        postDTO.setCategory_num(postEntity.getCategoryEntity().getCategoryNum());
        if (postEntity.getFileAttached() == null || postEntity.getFileAttached() == 0) {
            postDTO.setFile_attached(0);
        } else {
            List<String> fileList = new ArrayList<>();
            postDTO.setFile_attached(postEntity.getFileAttached());
            for (FileEntity fileEntity : postEntity.getFileEntityList()) {
                fileList.add(fileEntity.getFileUrl());
            }
            postDTO.setFile_url(fileList);
        }
        return postDTO;
    }

    /////////////////포스트랑 카테고리 엮은 부분//////////////
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static PostDTO from(PostEntity postEntity) {
        PostDTO dto = new PostDTO();
        dto.setPost_num(postEntity.getPostNum()); // camelCase로 변경
        dto.setPost_title(postEntity.getPostTitle());
        dto.setPost_content(postEntity.getPostContent());
        dto.setPost_price(postEntity.getPostPrice()); // snake_case 유지
        dto.setPost_saleState(postEntity.getPostSaleState());
        dto.setPost_regdate(postEntity.getPostRegdate());
        dto.setPost_update(postEntity.getPostUpdate());
        dto.setPost_location(postEntity.getPostLocation());
        dto.setPost_location2(postEntity.getPostLocation2());
        dto.setPost_way(postEntity.getPostWay());
        dto.setPost_hits(postEntity.getPostHits());
        dto.setPost_like(postEntity.getPostLike());
        dto.setPost_productState(postEntity.getPostProductState());
        dto.setUser_num(postEntity.getUserEntity().getUserNum());
        dto.setCategory_num(postEntity.getCategoryEntity().getCategoryNum());
        dto.setFile_attached(postEntity.getFileAttached()); // getter 사용
        if (postEntity.getFileAttached() == null || postEntity.getFileAttached() == 0) {
            dto.setFile_attached(0);
        } else {
            List<String> fileList = new ArrayList<>();
            dto.setFile_attached(postEntity.getFileAttached());
            for (FileEntity fileEntity : postEntity.getFileEntityList()) {
                fileList.add(fileEntity.getFileUrl());
            }
            dto.setFile_url(fileList);
        }
        return dto;
    }


    /////////////////포스트랑 유저 엮은 부분//////////////
/*    public void setUserNick(String user_nick) {
        this.user_nick = user_nick;
    }
    public void setUserPhoto(String user_photo) {
        this.user_photo = user_photo;
    }*/

/*    public void setUserDTO(UserDTO userDTO) {
    }*/
}