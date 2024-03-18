package com.hwagae.market.like;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LikeDTO {
    private int likeNum;
    private int userNum;
    private int postNum;

    public static LikeDTO toLikeDTO(LikeEntity likeEntity){
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setLikeNum(likeEntity.getLike_num());
        likeDTO.setPostNum(likeEntity.getPostEntity().getPostNum());
        likeDTO.setUserNum(likeEntity.getUserEntity().getUserNum());
        return likeDTO;
    }
}