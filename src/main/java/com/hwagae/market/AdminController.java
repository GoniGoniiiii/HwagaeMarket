
package com.hwagae.market;

import com.hwagae.market.event.EventDTO;
import com.hwagae.market.event.EventService;
import com.hwagae.market.inquiry.InquiryDTO;
import com.hwagae.market.inquiry.InquiryService;
import com.hwagae.market.notice.NoticeDTO;
import com.hwagae.market.notice.NoticeService;
import com.hwagae.market.report.ReportDTO;
import com.hwagae.market.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private EventService eventService;
    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private ReportService reportService;

    @GetMapping("/admin/adminMenu")
    public String adminMenu(Model model) {
        System.out.println("관리자 페이지 입장 쑈쑈쑈");
        // 전체 신고 수
        int totalReports = reportService.getTotalReports();
        // 처리 완료된 신고 수
        int completedReports = reportService.getCompletedReports();
        // 전체 신고 수에서 처리 완료된 신고 수를 뺀 값을 계산
        int unResolveReport = totalReports - completedReports;
        int totalQna = inquiryService.getTotalQna();
        // 처리 완료된 신고 수
        int completedQna = inquiryService.getCompletedQna();
        // 전체 신고 수에서 처리 완료된 신고 수를 뺀 값을 계산
        int unResolveQna = totalQna - completedQna;
        model.addAttribute("unResolveReport", unResolveReport);
        model.addAttribute("unResolveQna", unResolveQna);
        return "/views/admin/adminMenu";
        //userController에 admin id로 로그인해서 로그인 되면 adminMenu로 올수있게 설정해놔야됨
    }

    @GetMapping("admin/userList")
    public String userList() {
        System.out.println("회원 목록 리스트~");
        return "/views/admin/userList";
    }

    @GetMapping("admin/reportUserList")
    public String reportUserList() {
        System.out.println("제재 회원 리스트~");
        return "views/admin/reportUserList";
    }

    /* 관리자 컨트롤러*/
    @GetMapping("admin/insertNotice")
    public String insertNotice() {
        System.out.println(" 관리자 - 공지사항을 입력해보세요~");
        return "views/admin/insertNotice";
    }

    @GetMapping("admin/board/notice")
    public String noticeList(Model model) {
        List<NoticeDTO> noticeDTOList = noticeService.findAll();
        model.addAttribute("noticeList", noticeDTOList);
        System.out.println("model=" + model);
        System.out.println("관리자 - 공지사항 보러간다~");
        return "views/board/notice";
    }

    @GetMapping("admin/updateNotice")
    public String updateNotice() {
        System.out.println("관리자 - 공지사항 수정해보세용");
        return "views/admin/updateNotice";
    }

    @GetMapping("admin/inquiryAllManage/paging")
    public String inquiryAllManage(@PageableDefault(page = 1) Pageable pageable, Model model) {
        System.out.println("관리자 - 1:1 문의 확인할게요~");
        Page<InquiryDTO> inquiryList = inquiryService.paging(pageable);

        System.out.println("inquiryList: " + inquiryList);
        System.out.println("Total Pages: " + inquiryList.getTotalPages());
        System.out.println("Page Number: " + pageable.getPageNumber());
        //보여지는 페이지갯수를 처리하기 위한 변수
        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < inquiryList.getTotalPages()) ? startPage + blockLimit - 1 : inquiryList.getTotalPages();
        // 디버깅 메시지 출력
        System.out.println("Start Page: " + startPage);
        System.out.println("End Page: " + endPage);

        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "views/admin/inquiryAllManage";
    }
    @GetMapping("admin/inquiryManage/paging")
    public String inquiryManage(@PageableDefault(page = 1) Pageable pageable, Model model) {
        System.out.println("관리자 - 1:1 문의 확인할게요~");
        Page<InquiryDTO> inquiryList = inquiryService.paging(pageable);

        System.out.println("inquiryList: " + inquiryList);
        System.out.println("Total Pages: " + inquiryList.getTotalPages());
        System.out.println("Page Number: " + pageable.getPageNumber());
        //보여지는 페이지갯수를 처리하기 위한 변수
        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < inquiryList.getTotalPages()) ? startPage + blockLimit - 1 : inquiryList.getTotalPages();
        // 디버깅 메시지 출력
        System.out.println("Start Page: " + startPage);
        System.out.println("End Page: " + endPage);

        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "views/admin/inquiryManage";
    }



    @GetMapping("/admin/board/eventList")
    public String eventList(Model model) {
        List<EventDTO> eventDTOList = eventService.findAll();
        model.addAttribute("eventList", eventDTOList);
        System.out.println("model = " + model);
        System.out.println("관리자 - 이벤트 리스트 확인하러~");
        return "views/board/eventList";
    }

    @GetMapping("admin/insertEvent")
    public String insertEvent() {
        System.out.println("관리자 - 이벤트 입력할게요~");
        return "views/admin/insertEvent";
    }

    @GetMapping("admin/reportConfirm/paging")
    public String reportConfirm(@PageableDefault(page = 1) Pageable pageable, Model model) {
        System.out.println("관리자 - 신고 확인할게요~");
        Page<ReportDTO> reportList = reportService.paging(pageable);

        System.out.println("reportList: " + reportList);
        System.out.println("Total Pages: " + reportList.getTotalPages());
        System.out.println("Page Number: " + pageable.getPageNumber());
        //보여지는 페이지갯수를 처리하기 위한 변수
        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < reportList.getTotalPages()) ? startPage + blockLimit - 1 : reportList.getTotalPages();
        // 디버깅 메시지 출력
        System.out.println("Start Page: " + startPage);
        System.out.println("End Page: " + endPage);

        model.addAttribute("reportList", reportList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/views/admin/reportConfirm";
    }
    @GetMapping("admin/reportAllConfirm/paging")
    public String reportAllConfirm(@PageableDefault(page = 1) Pageable pageable, Model model) {
        System.out.println("관리자 - 신고 확인할게요~");
        Page<ReportDTO> reportList = reportService.paging(pageable);

        System.out.println("reportList: " + reportList);
        System.out.println("Total Pages: " + reportList.getTotalPages());
        System.out.println("Page Number: " + pageable.getPageNumber());
        //보여지는 페이지갯수를 처리하기 위한 변수
        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < reportList.getTotalPages()) ? startPage + blockLimit - 1 : reportList.getTotalPages();
        // 디버깅 메시지 출력
        System.out.println("Start Page: " + startPage);
        System.out.println("End Page: " + endPage);

        model.addAttribute("reportList", reportList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/views/admin/reportAllConfirm";
    }
}
