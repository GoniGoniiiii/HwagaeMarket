package com.hwagae.market.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/board/notice")
    public String noticeList(Model model){
        List<NoticeDTO> noticeDTOList =noticeService.findAll();
        model.addAttribute("noticeList",noticeDTOList);
        System.out.println("공지사항 보러가자~");
        return "/views/board/notice";
    }


    @PostMapping("admin/insertNotice")
    public String insertNotice(@ModelAttribute NoticeDTO noticeDTO) throws IOException {
        //noticeDTO에 담겨온 값을 Entity의 작성자 값으로 set하는과정
        System.out.println("noticeDTO = " + noticeDTO);
        noticeService.save(noticeDTO);
        return "redirect:/board/notice";
    }


    @GetMapping("notice/{notice_num}")
    public String findByNum(@PathVariable Integer notice_num,Model model ){
        NoticeDTO noticeDTO=noticeService.findByNum(notice_num);
        model.addAttribute("notice",noticeDTO);
        return "views/board/notice";
    }

    @GetMapping("admin/updateNotice/{notice_num}")
    public String updateForm(@PathVariable Integer notice_num, Model model){
        NoticeDTO noticeDTO= noticeService.findByNum(notice_num);
        model.addAttribute("noticeUpdate",noticeDTO);
        return "views/admin/updateNotice";
    }

    @PostMapping("/admin/updateNotice")
    public String update(@ModelAttribute NoticeDTO noticeDTO,Model model) throws Exception {
        NoticeDTO notice= noticeService.update(noticeDTO);
        model.addAttribute("notice",notice);
        return "redirect:/board/notice";
    }

    @GetMapping("admin/deleteNotice/{notice_num}")
    public String delete(@PathVariable Integer notice_num){
        noticeService.delete(notice_num);
        System.out.println("notice_num = " + notice_num);
        return "redirect:/board/notice";
    }




}