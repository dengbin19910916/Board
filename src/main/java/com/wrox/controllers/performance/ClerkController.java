package com.wrox.controllers.performance;

import com.wrox.controllers.Theme;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

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

    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String upload() {
        return "performance/upload";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam List<MultipartFile> files, Model model) {
        model.addAttribute("files", files);
        System.out.println("文件已被提交");
        for (MultipartFile file : files) {
            System.out.print(file.getOriginalFilename() + "   ");
        }

        return "performance/upload";
    }
}
