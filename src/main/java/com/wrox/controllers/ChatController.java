package com.wrox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newChat(Model model, HttpServletResponse response) {
        this.setNoCacheHeaders(response);
        model.addAttribute("chatSessionId", 0);

        return "chat/chat";
    }

    @RequestMapping("join/{chatSessionId}")
    public String joinChat(Model model, HttpServletResponse response, @PathVariable("chatSessionId") String chatSessionId) {
        this.setNoCacheHeaders(response);
        model.addAttribute("chatSessionId", chatSessionId);

        return "chat/chat";
    }

    private void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Expires", "Thu, 1 Jan 1970 12:00:00 GMT");
        response.setHeader("Cache-Control", "max-age=0, must-revalidate, no-cache");
        response.setHeader("Pragma", "no-cache");
    }
}
