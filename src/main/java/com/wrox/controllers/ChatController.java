package com.wrox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dengb on 2015/9/11.
 */
@Controller
@RequestMapping("chat")
public class ChatController {

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("sessions");

        return "chat/list";
    }
}
