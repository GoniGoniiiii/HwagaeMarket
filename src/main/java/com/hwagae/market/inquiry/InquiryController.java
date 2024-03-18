package com.hwagae.market.inquiry;

import com.hwagae.market.comment.CommentDTO;
import com.hwagae.market.comment.CommentService;
import com.hwagae.market.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;
    private final CommentService commentService;

    @GetMapping("myPage/inquiryList")
    public String inquiry(HttpSession session, Model model) {
        System.out.println("문의 목록 확인후 문의하러 갑시다용용");
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            Integer user_num = user.getUser_num();
            System.out.println("user_num" + user_num);
            // 사용자 번호를 이용하여 문의 목록 조회
            List<InquiryDTO> inquiryDTOList = inquiryService.findAllByUserNum(user_num);
            // 모델에 문의 목록 추가
            model.addAttribute("inquiryList", inquiryDTOList);
            return "/views/myPage/inquiryList";
        } else {
            return "views/user/login";

        }
    }


    @GetMapping("myPage/insertInquiry")
    public String inQuiry() {
        System.out.println("진짜 문의하러 갑시다~");
        return "views/myPage/insertInquiry";
    }

    @PostMapping("/myPage/insertInquiry")
    public String inquiryList(@ModelAttribute InquiryDTO inquiryDTO) {
        System.out.println("값이 있니? " + inquiryDTO.getUser_num());
        inquiryService.save(inquiryDTO);
        return "redirect:/myPage/inquiryList";
    }

    @GetMapping("/myPage/inquiry/{qna_num}")
    public String findByNum(@PathVariable Integer qna_num, Model model) {
        InquiryDTO inquiryDTO = inquiryService.findByNum(qna_num);

        List<CommentDTO> commentDTOList=commentService.qna_findAll(qna_num);
        model.addAttribute("commentList",commentDTOList);
        model.addAttribute("inquiry", inquiryDTO);
        return "views/myPage/inquiry";
    }

}