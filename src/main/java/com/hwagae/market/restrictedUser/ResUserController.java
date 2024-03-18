package com.hwagae.market.restrictedUser;

import com.hwagae.market.user.UserEntity;
import com.hwagae.market.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class ResUserController {
    private final ResUserService resUserService;
    private final UserService userService;

    @PostMapping("/admin/resUser")
    public String insertForm(@ModelAttribute ResUserDTO resUserDTO, @RequestParam("res_account") String resAccount, Model model){
        System.out.println("관리자 -  제재회원 입력해보아요~^^");
        System.out.println("resUserDTO = " + resUserDTO);
        model.addAttribute("resUserDTO",resUserDTO);
        model.addAttribute("resAccount",resAccount);
        return "/views/admin/insertResUser";
    }

    @PostMapping("/admin/insertResUser")
    public String insertResUser(@ModelAttribute ResUserDTO resUserDTO,Model model) throws IOException {
        System.out.println("관리자 - 제재 회원 입력중");
        System.out.println("resUserDTO = " + resUserDTO );

        UserEntity userEntity=userService.findByNum(resUserDTO.getUser_num());
        resUserService.save(resUserDTO,userEntity);
        model.addAttribute("resUserDTO",resUserDTO);
        return "/views/admin/adminMenu";
    }

    @GetMapping("/admin/resUserList/paging")
    public String resUserList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        System.out.println("관리자 - 1:1 문의 확인할게요~");
        Page<ResUserDTO> resUserList = resUserService.paging(pageable);

        System.out.println("resUserList: " + resUserList);
        System.out.println("Total Pages: " + resUserList.getTotalPages());
        System.out.println("Page Number: " + pageable.getPageNumber());
        //보여지는 페이지갯수를 처리하기 위한 변수
        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < resUserList.getTotalPages()) ? startPage + blockLimit - 1 : resUserList.getTotalPages();
        // 디버깅 메시지 출력
        System.out.println("Start Page: " + startPage);
        System.out.println("End Page: " + endPage);

        model.addAttribute("resUserList", resUserList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/views/admin/resUserList";
    }

    @PostMapping("/board/selectResUser")
    public ResponseEntity<Integer> selectResUser(@RequestBody Map<String, String> requestData){
        String selectValue = requestData.get("selectValue");
        String inputValue = requestData.get("inputValue");
        System.out.println(selectValue +"/ " + inputValue);
        Integer reportCount=resUserService.selectResUser(selectValue,inputValue);
        System.out.println(reportCount);
        return ResponseEntity.ok().body(reportCount);
    }

}
