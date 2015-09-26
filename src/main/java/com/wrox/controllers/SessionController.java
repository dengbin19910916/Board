package com.wrox.controllers;

import com.wrox.services.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by dengb on 2015/9/10.
 */
@Controller
@RequestMapping("session")
public class SessionController {

    @Inject
    private SessionRegistry sessionRegistry;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("timestamp", System.currentTimeMillis());
        model.addAttribute("numberOfSessions", this.sessionRegistry.getNumberOfSessions());
        model.addAttribute("sessionList", this.sessionRegistry.getAllSessions());

        return "session/list";
    }
}
