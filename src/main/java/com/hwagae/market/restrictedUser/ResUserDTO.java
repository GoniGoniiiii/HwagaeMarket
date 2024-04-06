package com.hwagae.market.restrictedUser;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResUserDTO {
    private Integer res_num;
    private String res_uname;
    private String res_uphone;
    private String res_uid;
    private String res_account;
    private String res_reason;
    private Integer report_count;
    private Integer user_num;

    public ResUserDTO(Integer resNum, String resUid, String resUname, String resUphone, String resAccount, String resReason,Integer reportCount) {
        this.res_num=resNum;
        this.res_uid=resUid;
        this.res_uname=resUname;
        this.res_uphone=resUphone;
        this.res_account=resAccount;
        this.res_reason=resReason;
        this.report_count=reportCount;
    }

    private static ResUserDTO toResUserDTO(ResUserEntity resUserEntity){
            ResUserDTO resUserDTO=new ResUserDTO();
            resUserDTO.setRes_num(resUserEntity.getResNum());
            resUserDTO.setRes_reason(resUserEntity.getResReason());
            resUserDTO.setUser_num(resUserEntity.getUserEntity().getUserNum());
            resUserDTO.setRes_uname(resUserEntity.getUserEntity().getUserName());
            resUserDTO.setRes_uphone(resUserEntity.getUserEntity().getUserPhone());
            resUserDTO.setRes_uid(resUserEntity.getUserEntity().getUserId());
            resUserDTO.setRes_account(resUserEntity.getResAccount());
            return resUserDTO;
    }


}
