package com.hwagae.market.notification;

import com.hwagae.market.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    @GetMapping("/notification/list")
    public String noticeList(Model model, HttpSession session ){
        UserDTO userDTO=(UserDTO) session.getAttribute("user");

        return "";


    }
}
