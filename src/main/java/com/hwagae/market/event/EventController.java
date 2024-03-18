package com.hwagae.market.event;

import com.hwagae.market.notice.NoticeDTO;
import com.hwagae.market.notice.NoticeService;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final NoticeService noticeService;

    @GetMapping("/board/eventList")
    public String eventList(Model model){
        List<EventDTO> eventDTOList=eventService.findAll();
        model.addAttribute("eventList",eventDTOList);
        System.out.println("model = "+model);
        System.out.println("관리자 - 이벤트 리스트 확인하러~");
        return "views/board/eventList";
    }


    @PostMapping("admin/insertEvent")
    public String insertEvent(@ModelAttribute EventDTO eventDTO) throws IOException {
        System.out.println("eventDTO = " + eventDTO);
        eventService.save(eventDTO);
        return "redirect:board/eventList";
    }

    @GetMapping("/board/event/{event_num}")
    public String findByNum(@PathVariable Integer event_num ,Model model){
        EventDTO eventDTO=eventService.findByNum(event_num);
        model.addAttribute("event",eventDTO);
        return "/views/board/event";
    }

    @GetMapping("admin/updateEvent/{event_num}")
    public String updateForm(@PathVariable Integer event_num,Model model){
        EventDTO eventDTO=eventService.findByNum(event_num);
        model.addAttribute("eventUpdate",eventDTO);
        System.out.println("event_num = " + event_num + ", model = " + model);
        return "views/admin/updateEvent";
    }

    @PostMapping("admin/updateEvent")
    public String updateEvent(@ModelAttribute EventDTO eventDTO,Model model) throws Exception {
        EventDTO event=eventService.update(eventDTO);
        model.addAttribute("event",event);
        System.out.println("eventDTO = " + eventDTO + ", model = " + model);
        return "redirect:/board/eventList";
    }

    @GetMapping("admin/deleteEvent/{event_num}")
    public String deleteEvent(@PathVariable Integer event_num){
        eventService.delete(event_num);
        return "redirect:/board/eventList";
    }


}