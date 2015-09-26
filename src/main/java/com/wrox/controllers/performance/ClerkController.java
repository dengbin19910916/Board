package com.wrox.controllers.performance;

import com.wrox.controllers.Theme;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

/**
 * Created by Dengbin on 2015/9/24.
 */
@Controller
@RequestMapping("/clerk")
public class ClerkController {

    @RequestMapping({"/portfolio"})
    public String portfolio(Model model, @PathParam("theme") String theme) {
        if (theme == null) {
            theme = Theme.DEFAULT.toString();
        }
        model.addAttribute("theme", "/resources/styles/kendo." + theme + ".min.css");
        return "performance/portfolio";
    }

    @RequestMapping("/indicator")
    public String indicator() {
        return "/performance/indicator";
    }
}
